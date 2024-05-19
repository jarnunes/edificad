package com.puc.edificad.web.controller.parametro;

import com.jnunes.spgauth.commons.utils.AuthUtils;
import com.jnunes.spgcore.commons.exceptions.ValidationException;
import com.jnunes.spgcore.services.dto.AutocompleteDto;
import com.jnunes.spgcore.web.CrudControllerSec;
import com.jnunes.spgcore.web.support.AjaxResponse;
import com.puc.edificad.commons.datatypes.DataTypeBoolean;
import com.puc.edificad.model.config.*;
import com.puc.edificad.services.PesquisaService;
import com.puc.edificad.services.config.ParametroService;
import com.puc.edificad.services.config.ValorParametroService;
import com.puc.edificad.web.support.cache.Cache;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/parametro")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW_PARAMETRO')")
public class ParametroController extends CrudControllerSec<Parametro> {

    private static final String CACHE_KEY_PARAMETRO_EDICAO = "parametroEdicao";
    public static final String URL_POST = "urlPOST";
    public static final String PATH_SAVE_LOGICO = "/save-logico";
    public static final String PATH_SAVE_NUMERICO = "/save-numerico";
    public static final String PATH_SAVE_JSON = "/save-json";

    private Cache cache;
    private ParametroService service;
    private ValorParametroService valorParametroService;
    private PesquisaService pesquisaService;

    @Autowired
    public void setService(ParametroService serviceIn) {
        this.service = serviceIn;
    }

    @Autowired
    void setPesquisaService(PesquisaService service) {
        this.pesquisaService = service;
    }

    @Autowired
    void setValorParametroService(ValorParametroService valorParametroService){
        this.valorParametroService = valorParametroService;
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

    @GetMapping("/configurar-logico")
    String configurarParametroLogico(Model model) {
        TipoParametroConfiguracao parametroEdicao = obterParametroParaEdicao();
        ValorParametro valorParametro = obterValorParametroParaEdicao(parametroEdicao);
        model.addAttribute("valorParametro", valorParametro);
        model.addAttribute(URL_POST, PATH_SAVE_LOGICO);
        return "parametro/create";
    }

    @GetMapping("/configurar-numerico")
    String configurarParametroNumerico(Model model) {
        TipoParametroConfiguracao parametroEdicao = obterParametroParaEdicao();
        ValorParametro valorParametro = obterValorParametroParaEdicao(parametroEdicao);
        model.addAttribute("valorParametro", valorParametro);
        model.addAttribute(URL_POST, PATH_SAVE_NUMERICO);
        return "parametro/create";
    }

    @GetMapping("/configurar-json")
    String configurarParametroJson(Model model) {
        TipoParametroConfiguracao parametroEdicao = obterParametroParaEdicao();
        ValorParametro valorParametro = obterValorParametroParaEdicao(parametroEdicao);
        model.addAttribute("valorParametro", valorParametro);
        model.addAttribute(URL_POST, PATH_SAVE_JSON);
        return "parametro/create";
    }

    private ValorParametro obterValorParametroParaEdicao(TipoParametroConfiguracao parametroConfiguracao) {
        Parametro instanciaParametro = parametroConfiguracao.getInstance();
        Optional<ValorParametro> valorParametro = instanciaParametro.getValoresParametro().stream().findFirst();
        if (valorParametro.isPresent())
            return valorParametro.get();

        ValorParametro novaInstancia = switch (parametroConfiguracao.getDType()) {
            case BOOLEAN -> new ValorParametroLogico();
            case NUMERIC -> new ValorParametroNumerico();
            case JSON -> new ValorParametroJson();
        };

        novaInstancia.setParametro(instanciaParametro);
        return novaInstancia;
    }

    private TipoParametroConfiguracao obterParametroParaEdicao(){
        return obterParametroNoCache().orElseThrow(() -> new ValidationException("parametro.obrigatorio.na.edicao"));
    }

    private Optional<TipoParametroConfiguracao> obterParametroNoCache(){
        return cache.getOptionalItem(getClass(), CACHE_KEY_PARAMETRO_EDICAO, AuthUtils.currentUserIdRequired())
                .map(TipoParametroConfiguracao.class::cast);
    }

    @PostMapping(PATH_SAVE_LOGICO)
    String saveLogico(ValorParametroLogico entity, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "parametro/create";
        final Long entityId = entity.getId();

        entity.getParametro().getNome().save(entity);

        addSuccess(attributes, entityId);
        return redirect("/parametro/update-logico", entity.getId());
    }

    @PostMapping(PATH_SAVE_NUMERICO)
    String saveNumerico(ValorParametroNumerico entity, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "parametro/create";
        final Long entityId = entity.getId();

        entity.getParametro().getNome().save(entity);

        addSuccess(attributes, entityId);
        return redirect("/parametro/update-numerico", entity.getId());
    }

    @PostMapping(PATH_SAVE_JSON)
    String saveJson(ValorParametroJson entity, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "parametro/create";
        final Long entityId = entity.getId();

        entity.getParametro().getNome().save(entity);

        addSuccess(attributes, entityId);
        return redirect("/parametro/update-json", entity.getId());
    }

    @GetMapping("/update-logico/{id}")
    String preUpdate(@PathVariable Long id, ModelMap modelMap) {
        Optional<ValorParametro> valorParametro= valorParametroService.findById(id);
        if(valorParametro.isPresent()){
            modelMap.addAttribute("valorParametro",valorParametro.get());
            modelMap.addAttribute(URL_POST, PATH_SAVE_LOGICO);
            return "parametro/create";
        }

        return "parametro";
    }

    @GetMapping("/update-numerico/{id}")
    String preUpdateNumerico(@PathVariable Long id, ModelMap modelMap) {
        Optional<ValorParametro> valorParametro= valorParametroService.findById(id);
        if(valorParametro.isPresent()){
            modelMap.addAttribute("valorParametro",valorParametro.get());
            modelMap.addAttribute(URL_POST, PATH_SAVE_NUMERICO);
            return "parametro/create";
        }

        return "parametro";
    }

    @GetMapping("/update-json/{id}")
    String preUpdateJson(@PathVariable Long id, ModelMap modelMap) {
        Optional<ValorParametro> valorParametro= valorParametroService.findById(id);
        if(valorParametro.isPresent()){
            modelMap.addAttribute("valorParametro",valorParametro.get());
            modelMap.addAttribute(URL_POST, PATH_SAVE_JSON);
            return "parametro/create";
        }

        return "parametro";
    }

    @PostMapping({"/delete"})
    ResponseEntity<AjaxResponse> deleteAll(@RequestBody List<Long> ids) {
        return super.deleteAll(ids, service::deleteById);
    }

    @ModelAttribute("parametroList")
    List<AutocompleteDto> parametroConfiguracaoList() {
        return pesquisaService.createFrom(TipoParametroConfiguracao.values(), null);
    }

    @ModelAttribute("dominioParametrosConfiguracaoList")
    List<AutocompleteDto> dominioParametrosConfiguracaoList() {
        return pesquisaService.createFrom(TipoDominioParametro.values(), null);
    }

    @ModelAttribute("booleanList")
    List<AutocompleteDto> booleanList() {
        return pesquisaService.createFrom(DataTypeBoolean.values(), null);
    }

    @PostMapping("/pesquisar")
    public String pesquisar(SearchForm searchForm) {
        adicionarParametroCache(searchForm.getParametro());
        return redirect("/parametro");
    }

    private void adicionarParametroCache(TipoParametroConfiguracao parametro){
        cache.addItem(this.getClass(), CACHE_KEY_PARAMETRO_EDICAO, AuthUtils.currentUserIdRequired(), parametro, 1800);
    }

    @Getter
    @Setter
    public static class SearchForm {
        TipoParametroConfiguracao parametro;
    }

}
