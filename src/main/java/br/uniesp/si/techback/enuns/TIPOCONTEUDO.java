package br.uniesp.si.techback.enuns;

public enum TIPOCONTEUDO {

    FILME("Filme", 1),
    SERIE("Série", 2);

    private final String  descricao;
    private final int id;

    // Constructor
    TIPOCONTEUDO(String descricao, int id) {
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
