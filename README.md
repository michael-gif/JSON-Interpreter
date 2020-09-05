# JSON-Interpreter
This java package will allow you to read json.

- It takes in a file with the json in it. For example `json.txt`
- It outputs an `ArrayList` which contains `HashMap`'s.  
- Each HashMap is a JSON object.  
- The HashMap's have `String` keys and `HashMapValue` values.  
- A `HashMapValue` instance has a `String` attribute for string values and an `ArrayList` attribute for nested JSON objects.  

## Usage
### Reading
- To read JSON, there is one availabe method in `Reader.java`
```java
public static ArrayList<HashMap<String, HashMapValue>> read(String filename) {}
```
- For example, doing `Reader.read("json.txt");` will return an `ArrayList<HashMap<String, HashMapValue>>`, which contains the JSON objects.
### Writing
- To write JSON, there are two availabe methods in `Writer.java`.  
```java
public static void writeString(ArrayList<HashMap<String, String>> data, String filename) {}
public static void writeHashMaps(ArrayList<HashMap<String, HashMapValue>> data, String filename, boolean append) {}
```
- The first method will write HashMap's with `String` keys and values. This is useful if you want to write some simple JSON objects.
- The second method will write HashMap's with `String` keys and `HashMapValue` values. This is if you want to write more complicated JSON objects, which involve lists.
- For example, doing  
```java
ArrayList<HashMap> list = new ArrayList<>();
HashMap<String, String> map = new HashMap<>();
map.put("name", "jeff");
list.add(map);
Writer.write(list, "json.txt");
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

Writer.writeHashMaps(list, "json.txt", true);
```
This would write  
`{"name":"hello","index":"0","value":[{"name":"goodbye","index":"0","value":"yes"}]},`
to `json.txt`
