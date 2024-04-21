package com.puc.edificad.web.controller.parametro;

import com.jnunes.spgcore.services.dto.AutocompleteDto;
import com.jnunes.spgcore.web.CrudControllerSec;
import com.jnunes.spgcore.web.support.AjaxResponse;
import com.jnunes.spgdatatable.DataTablePage;
import com.jnunes.spgdatatable.DataTableRequest;
import com.jnunes.spgdatatable.adapters.PageRequestAdapter;
import com.puc.edificad.commons.datatypes.DataTypeBoolean;
import com.puc.edificad.model.config.Parametro;
import com.puc.edificad.model.config.TipoDominioParametro;
import com.puc.edificad.model.config.TipoParametroConfiguracao;
import com.puc.edificad.services.PesquisaService;
import com.puc.edificad.services.config.ParametroService;
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

@Controller
@RequestMapping("/parametro")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW_PARAMETRO')")
public class ParametroController extends CrudControllerSec<Parametro> {

    ParametroService service;
    PesquisaService pesquisaService;
    Parametro parametroForm = new Parametro();

    @Autowired
    public void setService(ParametroService serviceIn) {
        this.service = serviceIn;
    }

    @Autowired
    void setPesquisaService(PesquisaService service){
        this.pesquisaService = service;
    }

    @Override
    protected ModelAndView getModelAndViewListPage() {
        return new ModelAndView("parametro/list");
    }

    @GetMapping
    ModelAndView entitiesList(ModelMap modelMap) {
        modelMap.put("searchForm", parametroForm);
        return getModelAndViewListPage();
    }

    @PostMapping
    @ResponseBody
    DataTablePage<Parametro> datatableList(@RequestBody DataTableRequest pagingRequest) {
        PageRequestAdapter adapter = new PageRequestAdapter(pagingRequest);
        Example<Parametro> example = Example.of(parametroForm);
        Page<Parametro> page = service.findAll(example, adapter.getPageRequest());
        DataTablePage<Parametro> dataTablePage = new DataTablePage<>();
        dataTablePage.setData(page.getContent());
        dataTablePage.setRecordsFiltered((int)page.getTotalElements());
        dataTablePage.setRecordsTotal(service.count().intValue());
        dataTablePage.setDraw(pagingRequest.getDraw());
        return dataTablePage;
    }

    @GetMapping("/configurar")
    String create(Model model) {
        model.addAttribute("entity", parametroForm);
        return "parametro/create";
    }

    @PostMapping("/save")
    String save(@RequestParam(name = "saveAndNew", defaultValue = "false") boolean saveAndNew,
        Parametro entity, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "voluntario/create";
        final Long entityId = entity.getId();

        service.save(entity);
        addSuccess(attributes, entityId);
        return saveAndNew ? redirect("/parametro/create") : redirect("/parametro/update", entity.getId());
    }

    @GetMapping("/update/{id}")
    String preUpdate(@PathVariable Long id, ModelMap modelMap) {
        Parametro entity = service.findById(id).orElse(new Parametro());
        modelMap.addAttribute("entity", entity);
        return "parametro/create";
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
    public String pesquisar(Parametro request){
        if(request.getNome() != null){
            request.setDominio(request.getNome().getDominio());
            request.setDType(request.getNome().getDType());
        }
        this.parametroForm = request;
        return redirect("/parametro");
    }
}
