package com.puc.edificad.web.controller.parametro;

import com.jnunes.spgcore.services.dto.AutocompleteDto;
import com.jnunes.spgcore.web.CrudControllerSec;
import com.jnunes.spgcore.web.support.AjaxResponse;
import com.jnunes.spgdatatable.DataTablePage;
import com.jnunes.spgdatatable.DataTableRequest;
import com.jnunes.spgdatatable.adapters.PageRequestAdapter;
import com.puc.edificad.commons.datatypes.DataTypeBoolean;
import com.puc.edificad.model.config.*;
import com.puc.edificad.services.PesquisaService;
import com.puc.edificad.services.config.ParametroService;
import com.puc.edificad.services.config.ValorParametroService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
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

    private static final String SEARCH_FORM = "searchForm";

    ParametroService service;
    ValorParametroService valorParametroService;
    PesquisaService pesquisaService;
    TipoParametroConfiguracao tipoParametro;
    Parametro parametro = new Parametro();

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

    @Override
    protected ModelAndView getModelAndViewListPage() {
        return new ModelAndView("parametro/list");
    }

    @GetMapping
    ModelAndView entitiesList(ModelMap modelMap, @ModelAttribute(SEARCH_FORM) final SearchForm form) {
        modelMap.put("searchFormParametro", form);

        if(form != null && form.getParametro() != null){
            modelMap.put("permitirConfiguracao", true);
            tipoParametro = form.getParametro();
        }

        return getModelAndViewListPage();
    }

    @PostMapping
    @ResponseBody
    DataTablePage<Parametro> datatableList(@RequestBody DataTableRequest pagingRequest) {
        PageRequestAdapter adapter = new PageRequestAdapter(pagingRequest);
        Example<Parametro> example = Example.of(parametro);
        Page<Parametro> page = service.findAll(example, adapter.getPageRequest());
        DataTablePage<Parametro> dataTablePage = new DataTablePage<>();
        dataTablePage.setData(page.getContent());
        dataTablePage.setRecordsFiltered((int) page.getTotalElements());
        dataTablePage.setRecordsTotal(service.count().intValue());
        dataTablePage.setDraw(pagingRequest.getDraw());
        return dataTablePage;
    }

    @GetMapping("/configurar-logico")
    String configurarParametroLogico(Model model, RedirectAttributes attributes) {
        if (tipoParametro == null) {
            addError(attributes, "Necessário selecionar um parâmetro para iniciar configuração.");
            redirect("/parametro");
        }

        Parametro instanciaParametro = tipoParametro.getInstance();
        Optional<ValorParametro> valorParametro = instanciaParametro.getValoresParametro().stream().findFirst();
        if(valorParametro.isPresent()){
            model.addAttribute("valorParametro", valorParametro.get());
            return "parametro/create-boolean";
        }

        if (instanciaParametro.getDType().isBoolean()) {
            ValorParametroLogico valorParametroLogico = new ValorParametroLogico();
            valorParametroLogico.setParametro(instanciaParametro);
            model.addAttribute("valorParametro", valorParametroLogico);
        } else if (instanciaParametro.getDType().isNumeric()) {
            ValorParametroNumerico valorParametroNumerico = new ValorParametroNumerico();
            valorParametroNumerico.setParametro(instanciaParametro);
            model.addAttribute("valorParametro", valorParametroNumerico);
        }

        return "parametro/create-boolean";
    }

    @GetMapping("/configurar")
    String create(Model model) {
        if(parametro.getId() != null && !parametro.getValoresParametro().isEmpty()){
            model.addAttribute("valorParametro",
                parametro.getValoresParametro().stream().findFirst().get());
            return "create-boolean";
        }

        if (parametro.getDType().isBoolean()) {
            ValorParametroLogico valorParametroLogico = new ValorParametroLogico();
            valorParametroLogico.setParametro(parametro);
            model.addAttribute("valorParametro", valorParametroLogico);
        } else if (parametro.getDType().isNumeric()) {
            ValorParametroNumerico valorParametro = new ValorParametroNumerico();
            valorParametro.setParametro(parametro);
            model.addAttribute("valorParametro", valorParametro);
        }

        return "create-boolean";
    }

    @PostMapping("/save")
    String save(@RequestParam(name = "saveAndNew", defaultValue = "false") boolean saveAndNew,
        ValorParametroLogico entity, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "parametro/create";
        final Long entityId = entity.getId();

        entity.getParametro().getNome().save(entity);

        addSuccess(attributes, entityId);
        return saveAndNew ? redirect("/parametro/create") : redirect("/parametro/update-logico", entity.getId());
    }

    @GetMapping("/update-logico/{id}")
    String preUpdate(@PathVariable Long id, ModelMap modelMap) {
        Optional<ValorParametro> valorParametro= valorParametroService.findById(id);
        if(valorParametro.isPresent()){
            modelMap.addAttribute("valorParametro",valorParametro.get());
            return "parametro/create-boolean";
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
    public String pesquisar(SearchForm searchForm, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(SEARCH_FORM, searchForm);
        return redirect("/parametro");
    }

    @Getter
    @Setter
    public static class SearchForm {
        TipoParametroConfiguracao parametro;
    }

}
