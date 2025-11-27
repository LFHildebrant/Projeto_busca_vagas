package com.utfpr.Projeto_Sistemas.entities;

public enum AreaJob {

    ADMINISTRACAO("Administração"),
    AGRICULTURA("Agricultura"),
    ARTES("Artes"),
    ATENDIMENTO_AO_CLIENTE("Atendimento ao Cliente"),
    COMERCIAL("Comercial"),
    COMUNICACAO("Comunicação"),
    CONSTRUCAO_CIVIL("Construção Civil"),
    CONSULTORIA("Consultoria"),
    CONTABILIDADE("Contabilidade"),
    DESIGN("Design"),
    EDUCACAO("Educação"),
    ENGENHARIA("Engenharia"),
    FINANCAS("Finanças"),
    JURIDICA("Jurídica"),
    LOGISTICA("Logística"),
    MARKETING("Marketing"),
    PRODUCAO("Produção"),
    RECURSOS_HUMANOS("Recursos Humanos"),
    SAUDE("Saúde"),
    SEGURANCA("Segurança"),
    TECNOLOGIA_DA_INFORMACAO("Tecnologia da Informação"),
    TELEMARKETING("Telemarketing"),
    VENDAS("Vendas"),
    OUTROS("Outros");

    private final String areaJob;

    AreaJob(String areaJob) {
        this.areaJob = areaJob;
    }

    public String getAreaJob() {
        return areaJob;
    }
    public static AreaJob from(String text) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }

        for (AreaJob area : AreaJob.values()) {
            if (area.name().equalsIgnoreCase(text) ||
                    area.getAreaJob() .equalsIgnoreCase(text)) {
                return area;
            }
        }

        throw new IllegalArgumentException("Invalid area: " + text);
    }
}
