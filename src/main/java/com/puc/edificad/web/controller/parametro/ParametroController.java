package com.puc.edificad.web.controller.parametro;

import com.jnunes.spgauth.commons.utils.AuthUtils;
import com.jnunes.spgauth.model.config.AuthEmailParameterType;
import com.jnunes.spgcore.commons.exceptions.ValidationException;
import com.jnunes.spgcore.services.dto.AutocompleteDto;
import com.jnunes.spgcore.web.CrudControllerSec;
import com.jnunes.spgparameter.ParameterMediator;
import com.jnunes.spgparameter.model.*;
import com.jnunes.spgparameter.services.ParameterValueService;
import com.puc.edificad.commons.datatypes.DataTypeBoolean;
import com.puc.edificad.model.config.TipoDominioParametro;
import com.puc.edificad.model.config.TipoParametroConfiguracao;
import com.puc.edificad.services.PesquisaService;
import com.puc.edificad.web.support.cache.Cache;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/parametro")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW_PARAMETRO')")
public class ParametroController extends CrudControllerSec<Parameter> {

    private static final String CACHE_KEY_PARAMETRO_EDICAO = "parametroEdicao";
    public static final String URL_POST = "urlPOST";
    public static final String PATH_SAVE_LOGICO = "/save-logico";
    public static final String PATH_SAVE_NUMERICO = "/save-numerico";
    public static final String PATH_SAVE_JSON = "/save-json";
    public static final String PATH_SAVE_HTML = "/save-html";

    private Cache cache;
    private PesquisaService pesquisaService;
    private ParameterValueService parameterValueService;

    @Autowired
    void setPesquisaService(PesquisaService service) {
        this.pesquisaService = service;
    }

    @Autowired
    void setParameterValueService(ParameterValueService parameterValueService){
        this.parameterValueService = parameterValueService;
    }

    @Autowired
    void setCache(Cache cache){
        this.cache = cache;
    }

    @Override
    protected ModelAndView getModelAndViewListPage() {
        return new ModelAndView("parametro/list");
    }

    @GetMapping
    ModelAndView entitiesList(ModelMap modelMap) {
        modelMap.put("searchForm", new SearchForm());

        obterParametroNoCache().ifPresent(param -> {
                 modelMap.put("instancia", param.getInstance());
                 modelMap.put("permitirConfiguracao", true);
             });

        return getModelAndViewListPage();
    }

    @GetMapping("/configurar")
    String configurarParametro(Model model) {
        final ParameterMediator parametroEdicao = obterParametroParaEdicao();
        final ParameterValue valorParametro = obterValorParametroParaEdicao(parametroEdicao);
        model.addAttribute("valorParametro", valorParametro);
        model.addAttribute(URL_POST, obterPathParaSalvarValorParametro(parametroEdicao.getDataType()));
        return "parametro/create";
    }

    private String obterPathParaSalvarValorParametro(DataType dataType) {
        return switch (dataType) {
            case BOOLEAN -> PATH_SAVE_LOGICO;
            case JSON -> PATH_SAVE_JSON;
            case HTML -> PATH_SAVE_HTML;
            default -> PATH_SAVE_NUMERICO;
        };
    }

    private ParameterValue obterValorParametroParaEdicao(ParameterMediator parametroConfiguracao) {
        Parameter instanciaParametro = parametroConfiguracao.getInstance();
        Optional<ParameterValue> valorParametro = instanciaParametro.getParameterValues().stream().findFirst();
        if (valorParametro.isPresent())
            return valorParametro.get();

        ParameterValue novaInstancia = parametroConfiguracao.getParameterValueNewInstance();
        novaInstancia.setParameter(instanciaParametro);
        return novaInstancia;
    }

    private ParameterMediator obterParametroParaEdicao(){
        return obterParametroNoCache().orElseThrow(() -> new ValidationException("parametro.obrigatorio.na.edicao"));
    }

    private Optional<ParameterMediator> obterParametroNoCache() {
        return cache.getOptionalItem(getClass(), CACHE_KEY_PARAMETRO_EDICAO, AuthUtils.currentUserIdRequired())
                .map(this::converterObjetoParaEnum);
    }

    private ParameterMediator converterObjetoParaEnum(Object param){
        final String parametro = (String) param;
        List<ParameterMediator> parametrosPermitidos = new ArrayList<>();
        parametrosPermitidos.addAll(Arrays.stream(TipoParametroConfiguracao.values()).toList());
        parametrosPermitidos.addAll(Arrays.stream(AuthEmailParameterType.values()).toList());
        return parametrosPermitidos.stream().filter(it -> it.name().equals(parametro)).findFirst().orElse(null);
    }

    @PostMapping(PATH_SAVE_LOGICO)
    String saveLogico(ParameterValueBoolean entity, BindingResult result, RedirectAttributes attributes) {
        return internalSave(entity, result, attributes);
    }

    @PostMapping(PATH_SAVE_NUMERICO)
    String saveNumerico(ParameterValueNumeric entity, BindingResult result, RedirectAttributes attributes) {
        return internalSave(entity, result, attributes);
    }

    @PostMapping(PATH_SAVE_JSON)
    String saveJson(ParameterValueJson entity, BindingResult result, RedirectAttributes attributes) {
        return internalSave(entity, result, attributes);
    }

    @PostMapping(PATH_SAVE_HTML)
    String saveHTML(ParameterValueHtml entity, BindingResult result, RedirectAttributes attributes) {
        return internalSave(entity, result, attributes);
    }

    private String internalSave(ParameterValue entity, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()) return "parametro/create";
        final Long entityId = entity.getId();

        parameterValueService.saveParameterValue(entity);

        addSuccess(attributes, entityId);
        return redirect("/parametro/update", entity.getId());
    }

    @GetMapping("/update/{id}")
    String preUpdateValorParametro(@PathVariable Long id, ModelMap modelMap) {
        Optional<ParameterValue> valorParametro= parameterValueService.findById(id);
        if(valorParametro.isPresent()){
            ParameterValue valor = valorParametro.get();
            modelMap.addAttribute("valorParametro", valor);
            modelMap.addAttribute(URL_POST, obterPathParaSalvarValorParametro(valor.getParameter().getDataType()));
            return "parametro/create";
        }

        return "parametro";
    }

    @ModelAttribute("parametroList")
    List<AutocompleteDto> parametroConfiguracaoList() {
        return pesquisaService.createFrom(new ArrayList<>(), null);
    }

    @ModelAttribute("booleanList")
    List<AutocompleteDto> booleanList() {
        return pesquisaService.createFrom(DataTypeBoolean.values(), null);
    }

    @ModelAttribute("parametrosTemplateHtml")
    Map<String, String> parametroDescriptions() {
        return obterParametroNoCache().map(ParameterMediator::getDescriptions).orElse(null);
    }

    @PostMapping("/pesquisar")
    public String pesquisar(SearchForm searchForm) {
        adicionarParametroCache(searchForm.getParametro());
        return redirect("/parametro");
    }

    private void adicionarParametroCache(String parametro){
        cache.addItem(this.getClass(), CACHE_KEY_PARAMETRO_EDICAO, AuthUtils.currentUserIdRequired(), parametro, 1800);
    }

    @Getter
    @Setter
    public  static class SearchForm {
        String parametro;
    }

}
