package Entities;

import java.util.ArrayList;
import java.util.List;

public abstract class  ModelMVC
{
	// liste des model pour les appels callbacks MVC
	private List<ModelMVC> listModel = new ArrayList<ModelMVC>();
	
	public void Attach(ModelMVC model)
	{
		// attachement du model mvc pour l'appel callback
		this.listModel.add(model);
	}
	
	public void UpdateAttachMVC()
	{
		// appel callback
		for(ModelMVC model : this.listModel)
		{
			model.UpdateMVC();
		}
	}

	public abstract void UpdateMVC();

}
