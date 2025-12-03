package com.utfpr.Projeto_Sistemas.controller;

import com.utfpr.Projeto_Sistemas.utilities.ActiveUsersStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/monitor")
public class MonitorController {
    @Autowired
    private ActiveUsersStore activeUserStore;

    @GetMapping(produces = "text/html") // Retorna HTML direto
    public String showActiveUsers() {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Monitor de IPs</title>");
        html.append("<meta http-equiv='refresh' content='60'>");// 60sec
        //html.append("<style>table {width: 50%; border-collapse: collapse; margin: 20px auto;} th, td {border: 1px solid #ddd; padding: 8px; text-align: center;} th {background-color: #f2f2f2;}</style>");
        //html.append("</head><body style='font-family: Arial; text-align: center;'>");

        html.append("<h1> Usuários Ativos (Últimos 10 min)</h1>");
        //html.append("<table><tr><th>IP</th><th>Última Ação</th></tr>");

        activeUserStore.getActiveUsers().forEach((ip, time) -> {
            html.append("<tr>");
            html.append("<td>").append(ip).append("</td>");
            html.append("<td>").append(time.format(DateTimeFormatter.ofPattern("HH:mm:ss"))).append("</td>");
            html.append("</tr>");
        });

        html.append("</table>");
        html.append("<p>Total: ").append(activeUserStore.getActiveUsers().size()).append("</p>");
        html.append("</body></html>");

        return html.toString();
    }
}
