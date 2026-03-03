import java.lang.*;
import java.util.*;
class GraphAdjList<T> implements IGraph<T> {
    HashMap<T, List<T>> edges = new HashMap<>();
    HashSet<T> vertices = new HashSet<>();

    public void addEdge(T src, T dest) {
        List<T> list = new ArrayList<T>();
        List<T> temp = edges.putIfAbsent(src, list);
        if (temp != null) {
            list = temp;
        }
        list.add(dest);
        vertices.add(src);
        vertices.add(dest);
    }

    public List<T> getAdjacent(T src) {
        List<T> result = edges.get(src);
        if (result == null) {
            return new ArrayList<T>();
        }
        return result;
    }

    public void addVertex(T vertex){
        edges.putIfAbsent(vertex, new ArrayList<T>());
        vertices.add(vertex);
    }

    public List<T> vertices() {
        return new ArrayList<>(vertices);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T v : vertices()) {
            sb.append("\n" + v + "->");
            sb.append(getAdjacent(v).toString());
        }
        return sb.toString();
    }
}