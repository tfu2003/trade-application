package persistence;

import model.Trades;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Some code was taken from the example JsonSerializationDemo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a writer that writes a JSON representation of the trades to a file
public class JsonWriter {

    // FIELDS
    private static final int TAB = 4;
    private PrintWriter file;
    private String fileLocation;

    // EFFECTS: Constructs a writer to write to file location
    public JsonWriter(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    // MODIFIES: this
    // EFFECTS: Opens the writer; if file cannot be found then throws FileNotFoundException
    public void open() throws FileNotFoundException {
        file = new PrintWriter(new File(fileLocation));
    }

    // MODIFIES: this
    // EFFECTS: Writes the JSON representation of the trades to the file
    public void write(Trades trades) {
        JSONObject jsonObject = trades.getJson();
        saveToFile(jsonObject.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: Closes the writer
    public void close() {
        file.close();
    }

    // MODIFIES: this
    // EFFECTS: Writes the given string to file
    public void saveToFile(String jsonObject) {
        file.print(jsonObject);
    }
}
