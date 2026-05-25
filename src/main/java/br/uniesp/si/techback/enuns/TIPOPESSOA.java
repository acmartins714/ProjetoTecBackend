package br.uniesp.si.techback.enuns;

public enum TIPOPESSOA {

    FISICA("Física", 1),
    JURIDICA("Jurídica", 2);

    private final String  descricao;
    private final int id;

    // Constructor
    TIPOPESSOA(String descricao, int id) {
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
