package com.puc.edificad.web.controller.relatorios;

import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.model.Voluntario;
import com.puc.edificad.services.BeneficiarioService;
import com.puc.edificad.services.CestaService;
import com.puc.edificad.services.DistribuicaoCestaService;
import com.puc.edificad.services.VoluntarioService;
import com.puc.edificad.services.dto.DistribuicaoCestaPorPeriodo;
import jakarta.servlet.http.HttpServletResponse;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/distribuicao-por-periodo")
public class DistribuicaoPorPeriodoController {

    private DistribuicaoCestaService service;

    private BeneficiarioService beneficiarioService;
    private VoluntarioService voluntarioService;
    private CestaService cestaService;


    @Autowired
    public void setService(DistribuicaoCestaService serviceIn) {
        this.service = serviceIn;
    }

    @Autowired
    private void setBeneficiarioService(BeneficiarioService serviceIn) {
        this.beneficiarioService = serviceIn;
    }

    @Autowired
    private void setVoluntarioService(VoluntarioService serviceIn) {
        this.voluntarioService = serviceIn;
    }

    @Autowired
    private void setCestaService(CestaService serviceIn) {
        this.cestaService = serviceIn;
    }

    @Autowired
    private ResourceLoader resourceLoader;

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
        List<DistribuicaoCestaPorPeriodo> distribuicao =
                service.obterDistribuicaoPorPeriodo(form.getInicio(), form.getFim(), form.getCesta(), form.getBeneficiario(),
                        form.getVoluntario());

        response.setHeader("Content-Disposition", "attachment; filename=distribuicao_cestas_por_periodo.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        try (OutputStream outputStream = response.getOutputStream()) {

            InputStream templateStream = resourceLoader.getResource("classpath:"
                + "templates/relatorios/distribuicao_por_periodo_template.xlsx").getInputStream();

            Context context = new Context();
            context.putVar("collection", distribuicao);

            JxlsHelper.getInstance().processTemplate(templateStream, outputStream, context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ModelAttribute("beneficiarioList")
    List<Beneficiario> beneficiarioList() {
        return beneficiarioService.findAll();
    }

    @ModelAttribute("voluntarioList")
    List<Voluntario> voluntarioList() {
        return voluntarioService.findAll();
    }

    @ModelAttribute("cestaList")
    List<Cesta> cestaList() {
        return cestaService.findAll();
    }
}
