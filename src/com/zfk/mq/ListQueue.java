package com.zfk.mq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * 只进被挤出固定大小队列
 * 
 * @date 2017年11月30日
 * @author 朱富昆
 */
public class ListQueue<E> {

	private int index = -1;

	private int capacity = 100;

	private int size = 0;

	Object[] elementData = {};

	public ListQueue() {
		elementData = new Object[capacity];
	}

	public ListQueue(int capacity) {
		this.capacity = capacity;
		elementData = new Object[capacity];
	}

	public boolean add(E e) {
		if (index == capacity - 1) {
			index = -1;
		}
		if (size != capacity) {
			size++;
		}
		elementData[++index] = e;
		return true;
	}

	@SuppressWarnings("unchecked")
	public E get(int index) {
		rangeCheck(index);
		return (E) elementData[index];
	}

	/**
	 * 得到最近num条记录
	 * 
	 * @param num
	 * @return
	 */
	public List<E> getRecent(int num) {
		List<E> list = new ArrayList<E>();
		num = size >= num ? num : size;
		int i = index + 1 - num;// i取数据首位
		if (i < 0) {
			i = capacity + i;
		}
		while (num-- > 0) {
			list.add(get(i++));
			if (i == capacity) {
				i = 0;
			}
		}
		return list;
	}

	/**
	 * 得到最近num条记录，refore几次前
	 * 
	 * @param num
	 * @return
	 */
	public List<E> getRecentBefore(int num, int before) {
		List<E> list = new ArrayList<E>();
		num = (size - num * before) >= num ? num : (size - num * before);
		int i = index + 1 - num * (before+1);
		if (i < 0) {
			i = capacity + i;
		}
		while (num-- > 0) {
			list.add(get(i++));
			if (i == capacity) {
				i = 0;
			}
		}
		return list;
	}

	/**
	 * 得到所有条记录
	 * 
	 * @param num
	 * @return
	 */
	public List<E> getAll() {
		List<E> list = new ArrayList<E>();
		int num = size;
		int i = index + 1 - num;
		if (i < 0) {
			i = capacity + i;
		}
		while (num-- > 0) {
			list.add(get(i++));
			if (i == capacity) {
				i = 0;
			}
		}
		return list;
	}

	public int index() {
		return index;
	}

	public int size() {
		return size;
	}

	private void rangeCheck(int index) {
		if (index >= capacity || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index, capacity));
	}

	private String outOfBoundsMsg(int index, int capacity) {
		return "Index: " + index + ", Capacity: " + capacity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageList").append(Arrays.toString(elementData));
		return builder.toString();
	}

	public static void main(String[] args) {
		ListQueue<String> list = new ListQueue<String>();
		for (int i = 1; i <= 105; i++) {
			list.add("data=" + i);
			// System.out.println("data=" + (i + 1) + " index==" + list.index()
			// + " size==" + list.size());
		}
		System.out.println(list);
		System.out.println(list.getAll());
		System.out.println(list.getRecent(10));
		System.out.println(list.getRecentBefore(10, 1));
		System.out.println(list.getRecentBefore(10, 2));
		System.out.println(list.getRecentBefore(10, 9));
		System.out.println(list.getRecentBefore(10, 10));

	}
}
