package ru.develgame.gpsviewer.renders;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateRender implements ComboitemRenderer<Date> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void render(Comboitem comboitem, Date date, int i) throws Exception {
        comboitem.setLabel(dateFormat.format(new java.util.Date(date.getTime())));
    }
}
