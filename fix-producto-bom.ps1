$encoding = New-Object System.Text.UTF8Encoding($false)

$producto = @'
package com.tecsup.model;

import com.tecsup.dto.CategoriaDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotNull
    @Positive
    private Double precio;

    @PositiveOrZero
    private int stock;

    private Long categoriaId;

    @Transient
    private CategoriaDTO categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }
}
'@

[System.IO.File]::WriteAllText('C:\Users\ronyc\git\config-repo\microservicios\producto-service\src\main\java\com\tecsup\model\Producto.java', $producto, $encoding)

$categoria = @'
package com.tecsup.dto;

public class CategoriaDTO {

    private Long id;
    private String nombre;
    private String estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
'@

[System.IO.File]::WriteAllText('C:\Users\ronyc\git\config-repo\microservicios\producto-service\src\main\java\com\tecsup\dto\CategoriaDTO.java', $categoria, $encoding)
