package sgh.mansilla.vista.abm;

import java.beans.PropertyEditorSupport;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.EnumSet;
import java.util.List;

import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import sgh.mansilla.modelo.datos.estadia.Estadia;
import sgh.mansilla.modelo.datos.estadia.EstadoEstadia;
import sgh.mansilla.modelo.datos.hotel.CaracteristicaHabitacion;
import sgh.mansilla.modelo.datos.hotel.Hotel;
import sgh.mansilla.modelo.datos.hotel.PlanAlojamiento;
import sgh.mansilla.modelo.datos.hotel.TipoHabitacion;
import sgh.mansilla.modelo.negocio.ABM;
import sgh.mansilla.modelo.negocio.estadia.EstadoEstadiaABM;
import sgh.mansilla.modelo.negocio.hotel.PlanAlojamientoABM;
import sgh.mansilla.modelo.negocio.hotel.TipoHabitacionABM;

@Controller
@RequestMapping("/estadia")
public class EstadiaABMController extends AbstractABMController<Integer, Estadia> {


	@Autowired
	EstadoEstadiaABM estadoEstadiaABM;
	@Autowired
	PlanAlojamientoABM planAlojamientoABM;

	@Autowired
	@Qualifier("estadiaABM")
	protected void setAbm(ABM<Integer, Estadia> abm) {
		super.setAbm(abm);
	}

	public EstadiaABMController() {
		super("abm/estadia");
	}


	@Override
	protected Estadia createEntity() {
		return new Estadia();
	}

	@ModelAttribute("planAlojamiento")
	public List<PlanAlojamiento> initializeProfiles() {
		return planAlojamientoABM.listar();
	}


	@ModelAttribute("estadoEstadia")
	public List<EstadoEstadia> initializeProfiles2() {
		return estadoEstadiaABM.listar();
	}

	@InitBinder
    public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "diaCheckIn", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, "diaCheckOut", new CustomDateEditor(dateFormat, true));

		binder.registerCustomEditor(PlanAlojamiento.class, new PlanAlojamientoEditor());
		binder.registerCustomEditor(EstadoEstadia.class, new EstadoEstadiaEditor());

    }

	private class PlanAlojamientoEditor extends PropertyEditorSupport {

		private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

		@Override
		public void setAsText(String text) {
			if (text == null || 0 == text.length()) {
				setValue(null);
				return;
			}

			setValue(planAlojamientoABM.buscarPorId(typeConverter.convertIfNecessary(text, Integer.class)));
		}
	}


	private class EstadoEstadiaEditor extends PropertyEditorSupport {

		private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

		@Override
		public void setAsText(String text) {
			if (text == null || 0 == text.length()) {
				setValue(null);
				return;
			}

			setValue(estadoEstadiaABM.buscarPorId(typeConverter.convertIfNecessary(text, Integer.class)));
		}
	}

}