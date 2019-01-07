package com.zz.bms.util.charts.vo.echarts;

import com.zz.bms.util.charts.vo.echarts.base.Grid;
import com.zz.bms.util.charts.vo.echarts.base.VisualMap;
import com.zz.bms.util.charts.vo.echarts.base.Xaxis;
import com.zz.bms.util.charts.vo.echarts.base.Yaxis;
import com.zz.bms.util.charts.vo.echarts.legend.Legend;
import com.zz.bms.util.charts.vo.echarts.series.Series;
import com.zz.bms.util.charts.vo.echarts.title.Title;
import com.zz.bms.util.charts.vo.echarts.tooltip.Tooltip;

@SuppressWarnings("serial")
public class Option implements  java.io.Serializable{
	
	private String backgroundColor;
	private String[] color;
	
	private Title[] title;
	private Legend[] legend;
	private Series[] series;
	private Tooltip[] tooltip;
	private VisualMap[] visualMap;
	
	private Grid[] grid;
	
	private Xaxis[] xaxis ;
	private Yaxis[] yaxis ;
	
	
	public Legend[] getLegend() {
		return legend;
	}
	public void setLegend(Legend[] legend) {
		this.legend = legend;
	}
	public Series[] getSeries() {
		return series;
	}
	public void setSeries(Series[] series) {
		this.series = series;
	}
	public Tooltip[] getTooltip() {
		return tooltip;
	}
	public void setTooltip(Tooltip[] tooltip) {
		this.tooltip = tooltip;
	}
	public Title[] getTitle() {
		return title;
	}
	public void setTitle(Title[] title) {
		this.title = title;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String[] getColor() {
		return color;
	}
	public void setColor(String[] color) {
		this.color = color;
	}
	public Grid[] getGrid() {
		return grid;
	}
	public void setGrid(Grid[] grid) {
		this.grid = grid;
	}
	public VisualMap[] getVisualMap() {
		return visualMap;
	}
	public void setVisualMap(VisualMap[] visualMap) {
		this.visualMap = visualMap;
	}
	public Xaxis[] getXaxis() {
		return xaxis;
	}
	public void setXaxis(Xaxis[] xaxis) {
		this.xaxis = xaxis;
	}
	public Yaxis[] getYaxis() {
		return yaxis;
	}
	public void setYaxis(Yaxis[] yaxis) {
		this.yaxis = yaxis;
	}

	
	
	

}
