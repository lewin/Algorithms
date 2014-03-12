package Trees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

// A custom heap that is backed by a hashmap and arraylist
public class QriorityPueue<E> {
  private int size;
  private ArrayList<E> list;
  private HashMap<E, Integer> pos;
  private Comparator<E> comp;

  public QriorityPueue(Comparator<E> comp) {
    size = 0;
    list = new ArrayList<E>();
    pos = new HashMap<E, Integer>();
    this.comp = comp;
  }

  public boolean add(E e) {
    if (e == null)
      throw new NullPointerException();
    list.add(e);
    pos.put(e, size);
    bubbleUp(size, e);
    size++;
    return true;
  }

  public E poll() {
    if (size == 0)
      return null;
    size--;
    E ret = list.get(0);
    pos.remove(ret);
    E last = list.get(size);
    list.remove(size);
    if (size != 0)
      bubbleDown(0, last);
    return ret;
  }

  public E peek() {
    if (size == 0)
      return null;
    return list.get(0);
  }

  public boolean updateKey(E e) {
    Integer ps = pos.get(e);
    if (ps == null)
      return false;

    // try moving it up and down
    bubbleUp(ps, e);
    ps = pos.get(e);
    bubbleDown(ps, e);

    return true;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public boolean remove(E e) {
    Integer ps = pos.get(e);
    if (ps == null)
      return false;
    pos.remove(e);
    size--;
    E last = list.get(size);
    if (size != 0)
      bubbleDown(ps, last);
    return true;
  }

  public boolean contains(E e) {
    return pos.containsKey(e);
  }

  public Iterator<E> iterator() {
    return list.iterator();
  }

  private void bubbleUp(int idx, E e) {
    while (idx > 0) {
      int par = (idx - 1) >> 1;
      E toComp = list.get(par);
      if (comp.compare(e, toComp) >= 0) // greater than parent
        break;
      list.set(idx, toComp);
      pos.put(toComp, idx);
      idx = par;
    }
    list.set(idx, e);
    pos.put(e, idx);
  }

  private void bubbleDown(int idx, E e) {
    int half = size >> 1;
    while (idx < half) {
      int next = (idx << 1) + 1, test = next + 1;
      E toComp = list.get(next);
      // get biggest child
      if (test < size && comp.compare(toComp, list.get(test)) > 0)
        toComp = list.get(next = test);
      if (comp.compare(e, toComp) <= 0) // less than biggest child
        break;
      list.set(idx, toComp);
      pos.put(toComp, idx);
      idx = next;
    }
    list.set(idx, e);
    pos.put(e, idx);
  }
}
