package ru.develgame.gpsviewer.composers;

import org.springframework.web.client.RestTemplate;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.LatLng;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import ru.develgame.gpsdomain.GPSReceivedData;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Set;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GoogleMapsComposer extends SelectorComposer<Component>{
	@Wire
	private Gmaps gmaps;

	@Wire
	private Combobox dateComboBox;

	@Wire
	private Listbox pointsList;

	@WireVariable
	private RestTemplate restTemplate;

	private ListModelList<Date> dateModel;

	private ListModelList<GPSReceivedData> pointsDataModel = null;

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yy");

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		Date[] allDates = restTemplate.getForObject("http://localhost:8111/getAllDates", Date[].class);
		if (allDates != null) {
			for (Date elem : allDates) {
				getDateModel().add(elem);
			}
		}

		pointsList.setModel(pointsDataModel);
	}

	public ListModelList<Date> getDateModel() {
		if (dateModel == null)
			dateModel = new ListModelList<>();

		return dateModel;
	}

	public ListModelList<GPSReceivedData> getPointsDataModel() {
		if (pointsDataModel == null) {
			pointsDataModel = new ListModelList<>();
		}

		return pointsDataModel;
	}

	@Listen("onSelect = #dateComboBox")
	public void dateComboBoxOnSelect() {
		Set<Date> selection = dateModel.getSelection();
		if (!selection.isEmpty()) {
			GPSReceivedData[] gpsReceivedData = restTemplate.getForObject("http://localhost:8111/data?date={date}",
					GPSReceivedData[].class,
					selection.stream().findFirst().get()
					);
			if (gpsReceivedData != null && gpsReceivedData.length > 0) {
				gmaps.setCenter(new LatLng(gpsReceivedData[0].getLatitude(), gpsReceivedData[0].getLongitude()));

				for (GPSReceivedData elem : gpsReceivedData) {
					Gmarker gmarker = new Gmarker();
					gmarker.setLat(elem.getLatitude());
					gmarker.setLng(elem.getLongitude());
					gmarker.setTooltiptext(dateFormat.format(elem.getTime()));
					gmaps.appendChild(gmarker);
				}

				pointsDataModel = new ListModelList<>(gpsReceivedData);
				pointsList.setModel(pointsDataModel);
			}
		}
	}
}
