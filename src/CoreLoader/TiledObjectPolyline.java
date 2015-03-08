package CoreLoader;

import java.util.ArrayList;
import java.util.List;

public class TiledObjectPolyline extends TiledObjectBase 
{
	// liste des points formant le polyline
	private List<TiledObjectPolylinePoint> listPoint;
	
	public TiledObjectPolyline()
	{
		this.setTypeObjects(Type.POLYLINE);
		// instance de la list
		listPoint = new ArrayList<TiledObjectPolylinePoint>();
	}
	
	public TiledObjectPolyline(Type t)
	{
		super(t);
		// instance de la list
		listPoint = new ArrayList<TiledObjectPolylinePoint>();
	}
	
	public void InsertPoint(int x,int y)
	{
		// on instantie un point
		TiledObjectPolylinePoint p = new TiledObjectPolylinePoint(x,y);
		// on ajoute dans la liste des points
		listPoint.add(p);
		
	}
	
	public TiledObjectPolylinePoint GetPoint(int ind)
	{
		if(ind > -1 && ind < this.listPoint.size())
		{
			// si l'indice se trouve entre 0 et le max de la liste des points
			return this.listPoint.get(ind);
		}
		
		return null;
	}

	/**
	 * @return the listPoint
	 */
	public List<TiledObjectPolylinePoint> getListPoint() {
		return listPoint;
	}

	
	
	
}
