package br.uniesp.si.techback.enuns;

public enum STATUSASSINATURA {
    ATIVA("Ativa", 1),
    EM_ATRASO("Em Atraso", 2),
    CANCELADA("Cancelada", 3);

    private final String  descricao;
    private final int id;

    // Constructor
    STATUSASSINATURA(String descricao, int id) {
        this.descricao = descricao;
        this.id = id;
    }

    // Getters and Setters
    public String getDescricao() {
        return descricao;
    }

    public int getId() {
        return id;
    }
}
