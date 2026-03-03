import java.lang.*;
import java.util.*;
interface IGraph<T>{
    void addEdge(T src, T dest);
    void addVertex(T v);
    List<T> getAdjacent(T src);
    List<T> vertices();
    String toString();
}