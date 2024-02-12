package com.puc.edificad.web.controller.relatorios;

import com.jnunes.spgcore.commons.exceptions.ValidationException;
import com.jnunes.spgcore.services.dto.AutocompleteDto;
import com.puc.edificad.services.*;
import com.puc.edificad.services.dto.DistribuicaoCestaPorPeriodo;
import jakarta.servlet.http.HttpServletResponse;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/distribuicao-por-periodo")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW_RELATORIOS')")
public class DistribuicaoPorPeriodoController {

    private DistribuicaoCestaService service;

    private PesquisaService pesquisaService;
    private ResourceLoader resourceLoader;

    @Autowired
    public void setService(DistribuicaoCestaService serviceIn) {
        this.service = serviceIn;
    }

    @Autowired
    public void setPesquisaService(PesquisaService pesquisaServiceIn) {
        this.pesquisaService = pesquisaServiceIn;
    }

    @Autowired
    void setResourceLoader(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    @GetMapping
    public String index(ModelMap modelMap) {
        ConsultaPorPeriodoForm form = new ConsultaPorPeriodoForm();
        form.setInicio(LocalDate.now().plusDays(-30));
        form.setFim(LocalDate.now());

        modelMap.addAttribute("formConsulta", form);
        return "relatorios/distribuicao-cestas-por-periodo-consulta";
    }

    @PostMapping("/consultar")
    public void downloadExel(ConsultaPorPeriodoForm form, HttpServletResponse response) {
        List<DistribuicaoCestaPorPeriodo> distribuicao = service.obterDistribuicaoPorPeriodo(form.getInicio(),
            form.getFim(), form.getCesta(), form.getBeneficiario(), form.getVoluntario());

        response.setHeader("Content-Disposition", "attachment; filename=distribuicao_cestas_por_periodo.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        try (OutputStream outputStream = response.getOutputStream()) {

            InputStream templateStream = resourceLoader.getResource("classpath:"
                + "templates/relatorios/distribuicao_por_periodo_template.xlsx").getInputStream();

            Context context = new Context();
            context.putVar("collection", distribuicao);

            JxlsHelper.getInstance().processTemplate(templateStream, outputStream, context);
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
    }

    @GetMapping("/complete-beneficiarios")
    public ResponseEntity<List<AutocompleteDto>> complete(@RequestParam(value = "q", required = false) String query) {
        return ResponseEntity.ok(pesquisaService.obterBeneficiarios(query));
    }

}
