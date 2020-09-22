# JSON-Interpreter
This java package will allow you to read json.

- It takes in a file with the json in it. For example `json.txt`
- It outputs an `ArrayList` which contains `HashMap`'s.  
- Each HashMap is representative of a JSON object.  
- The HashMap's have `String` keys and either `String` or `HashMapValue` values.  
- A `HashMapValue` instance has a `String` attribute for string values and an `ArrayList` attribute for nested JSON objects.  

## Usage
### Reading
- To read JSON, there is one availabe method in `Interpreter.java`
```java
public static ArrayList<HashMap<String, HashMapValue>> read(String filename) {}
```
- For example, doing `Interpreter.read("json.txt");` will return an `ArrayList<HashMap<String, HashMapValue>>`, which contains the JSON objects in the form of `HashMap`'s.
### Writing
- To write JSON, there are two availabe methods in `Interpreter.java`.  
```java
public static void writeString(ArrayList<HashMap<String, String>> data, String filename) {}
public static void writeCompound(ArrayList<HashMap<String, HashMapValue>> data, String filename, boolean append) {}
```
- The first method will write HashMap's with `String` keys and values. This is useful if you want to write simple JSON objects, such as `{"name":"bob"}`.
- The second method will write HashMap's with `String` keys and `HashMapValue` values. This is if you want to write more complicated JSON objects, which involve lists. Set `boolean append` to `true` if you want to append to the file, or `false` if you want to overwrite the file. The default value is `true`.
- For example, doing  
```java
ArrayList<HashMap<String, String>> list = new ArrayList<>();
HashMap<String, String> map = new HashMap<>();
map.put("name", "jeff");
list.add(map);
Interpreter.writeString(list, "json.txt");
```
will write `{"name":"jeff"}` to the next line of `json.txt`.
- Another example,
```java
ArrayList<HashMap<String, HashMapValue>> nestedList = new ArrayList<>();
HashMap<String, HashMapValue> secondMap = new HashMap<>();
secondMap.put("index",new HashMapValue("0"));
secondMap.put("name",new HashMapValue("goodbye"));
secondMap.put("value",new HashMapValue("yes"));
nestedList.add(secondMap);

ArrayList<HashMap<String, HashMapValue>> list = new ArrayList<>();
HashMap<String, HashMapValue> map = new HashMap<>();
map.put("index",new HashMapValue("0"));
map.put("name",new HashMapValue("hello"));
map.put("value",new HashMapValue(nestedList));
list.add(map);

Interpreter.writeCompound(list, "json.txt", true);
```
This would write  
`{"name":"hello","index":"0","value":[{"name":"goodbye","index":"0","value":"yes"}]},`
to `json.txt`
