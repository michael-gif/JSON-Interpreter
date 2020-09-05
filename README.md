# JSON-Interpreter
This java package will allow you to read json.

- It takes in a file with the json in it. For example `json.txt`
- It outputs an `ArrayList` which contains `HashMaps`.  
- Each HashMap is a JSON object.  
- The HashMap's have `String` keys and `HashMapValue` values.  
- A `HashMapValue` instance has a `String` attribute for string values and an `ArrayList` attribute for nested JSON objects.  

## Usage
### Reading
- To read JSON, use the static `read(String filename);` method in `Reader.java`.
- For example, doing `Reader.read("json.txt");` will return an `ArrayList<HashMap<String, HashMapValue>>`, which contains the JSON objects.
### Writing
- To write JSON, use the staic `write(HashMap<String, String> data, String filename);` method in `Writer.java`.
- For example, doing  
```
HashMap<String, String> map = new HashMap<>();
map.put("name", "jeff");
Writer.write(map, "json.txt");
```
will write `{"name":"jeff"}` to the next line of `json.txt`.
