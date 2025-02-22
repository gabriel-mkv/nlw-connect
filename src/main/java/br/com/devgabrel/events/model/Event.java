package br.com.devgabrel.events.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidade que representa um evento.
 */
@Entity
@Table(name = "tbl_event")
public class Event {
    /**
     * Identificador único do evento, gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer eventId;

    /**
     * Título do evento. Não pode ser nulo e tem um tamanho máximo de 255 caracteres.
     */
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    /**
     * "Pretty name" do evento, usado para URLs amigáveis. Não pode ser nulo, deve ser único e tem um tamanho máximo de 50 caracteres.
     */
    @Column(name = "pretty_name", length = 50, nullable = false, unique = true)
    private String prettyName;

    /**
     * Local onde o evento será realizado. Não pode ser nulo e tem um tamanho máximo de 255 caracteres.
     */
    @Column(name = "location", length = 255, nullable = false)
    private String location;

    /**
     * Preço do evento. Não pode ser nulo.
     */
    @Column(name = "price", nullable = false)
    private Double price;

    /**
     * Data de início do evento.
     */
    @Column(name = "start_date")
    private LocalDate startDate;

    /**
     * Data de término do evento.
     */
    @Column(name = "end_date")
    private LocalDate endDate;

    /**
     * Horário de início do evento.
     */
    @Column(name = "start_time")
    private LocalTime startTime;

    /**
     * Horário de término do evento.
     */
    @Column(name = "end_time")
    private LocalTime endTime;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public void setPrettyName(String prettyName) {
        this.prettyName = prettyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}