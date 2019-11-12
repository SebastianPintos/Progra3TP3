package repoLocal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import memento.CareTaker;

public class RepoLocal {

	public void generarJSON(String archivo, Object o) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(o);

		try {
			FileWriter writer = new FileWriter(archivo + ".JSON");
			writer.write(json);
			writer.close();

		} catch (Exception e) {
			return;
		}
	}

	public void agregarAMemoria(Object o, String arch) {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String jsonString = gson.toJson(o);
			FileWriter writer = new FileWriter(arch + ".JSON");

			writer.write(jsonString);
			writer.close();
		}

		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public CareTaker leerJSON(String archivo) {
		Gson gson = new Gson();
		CareTaker ret = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(archivo + ".JSON"));
			ret = gson.fromJson(br, CareTaker.class);
		} catch (Exception e) {
		}
		return ret;
	}
}
