package br.edu.utfpr.springapi.enums;

public enum EventType {
    APPLICATION_CREATED("Nova aplicação criada"),
    APPLICATION_UPDATED("Aplicação atualizada"),
    APPLICATION_STATUS_UPDATED("Status da aplicação atualizado"),
    APPLICATION_COMPLETED("Aplicação finalizada"),
    APPLICATION_DELETED("Aplicação removida");

    private final String description;

    EventType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}