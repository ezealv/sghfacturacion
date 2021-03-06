package sgh.mansilla.modelo.negocio.facturacion.Abm;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import sgh.mansilla.modelo.dao.DaoGenerico;
import sgh.mansilla.modelo.dao.AbstractDao.OrderType;
import sgh.mansilla.modelo.datos.facturacion.Moneda;
import sgh.mansilla.modelo.negocio.ABMGenerico;

@Service("monedaABM")
@Transactional
public class DefaultMonedaABM extends ABMGenerico<Integer, Moneda> implements MonedaABM {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	@Autowired
	@Qualifier("monedaDao")
	public void setDao(DaoGenerico<Integer, Moneda> dao) {
		super.setDao(dao);
	}

	@Override
	public void guardar(Moneda entidad) {
		super.guardar(entidad);
	}

	@Override
	protected void actualizarEntidad(Moneda entidadPersistida, Moneda entidadActualizada) {
		BeanUtils.copyProperties(entidadActualizada, entidadPersistida, "idMoneda");
	}

	@Override
	public List<Moneda> listar() {
		return dao.list(true, OrderType.ASCENDING, "idMoneda");
	}
}