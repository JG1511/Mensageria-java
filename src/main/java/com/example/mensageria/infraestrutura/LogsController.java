package com.example.mensageria.infraestrutura;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/adm/logs")
public class LogsController {

    private final LogMensagemRepository repository;

    public LogsController(LogMensagemRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<LogMensagem> json() {
        return repository.findAllByOrderByDataHoraDesc();
    }

    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<LogMensagem> jsonExplicito() {
        return json();
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String paginaRaiz() {
        return pagina();
    }

    @GetMapping(value = "/pagina", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String pagina() {
        StringBuilder html = new StringBuilder(
                "<!doctype html><html lang=\"pt-BR\"><head><meta charset=\"UTF-8\">" +
                "<title>Logs da mensageria</title>" +
                "<style>body{font-family:Arial;margin:2rem}table{border-collapse:collapse;width:100%}" +
                "th,td{border:1px solid #ccc;padding:.4rem;text-align:left}th{background:#eee}</style>" +
                "</head><body><h1>Logs da mensageria</h1><table><thead><tr>" +
                "<th>Data</th><th>Tipo</th><th>Fila</th><th>Entidade</th><th>Operação</th>" +
                "<th>Pessoa</th><th>Mensagem</th></tr></thead><tbody>");
        for (LogMensagem log : repository.findAllByOrderByDataHoraDesc()) {
            html.append("<tr><td>").append(escape(log.getDataHora()))
                    .append("</td><td>").append(escape(log.getTipo()))
                    .append("</td><td>").append(escape(log.getFila()))
                    .append("</td><td>").append(escape(log.getEntidade()))
                    .append("</td><td>").append(escape(log.getOperacao()))
                    .append("</td><td>").append(escape(log.getPessoa()))
                    .append("</td><td>").append(escape(log.getMensagem()))
                    .append("</td></tr>");
        }
        return html.append("</tbody></table></body></html>").toString();
    }

    private String escape(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString().replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }
}
