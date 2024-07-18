package org.maxim.RestApi.model;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Integer id;
    @Column(name = "name")
    @Expose
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Expose
    private List<Event> events;

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "'}";
    }
}