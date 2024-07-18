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
@Table(name = "files")
public class UFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Integer id;
    @Column(name = "name")
    @Expose
    private String name;
    @Column(name = "file_path")
    @Expose
    private String filePath;
    @OneToMany(mappedBy = "uFile", fetch = FetchType.LAZY)
    private List<Event> events;

    @Override
    public String toString() {
        return "UFile{id=" + id + ", name='" + name + "'}";
    }
}