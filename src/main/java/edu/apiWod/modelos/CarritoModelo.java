package edu.apiWod.modelos;

import jakarta.persistence.*;

/**
 * Se almacena los campos del carrito
 * msm- - 230525
 */
@Entity
@Table(name = "carrito", schema = "logica_negocio")
public class CarritoModelo {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

	@Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "articulo_id", nullable = false)
    private Long articuloId;

    @Column(name = "cantidad", nullable = false)    
    private Integer cantidad;

    
    //Getter y setters
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Long getArticuloId() {
		return articuloId;
	}

	public void setArticuloId(Long articuloId) {
		this.articuloId = articuloId;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
    
}
