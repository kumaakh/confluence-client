package com.ak.confluence.client.page.wiki;

import com.ak.confluence.client.page.PageElement;
import com.ak.confluence.client.page.Text;

public class TaskList extends PageElement {

	public class ItemBody extends PageElement {
		protected ItemBody() {
			super("ac:task-body");
		}
	}
	class ItemStatus extends PageElement {
		protected ItemStatus(boolean isComplete) {
			super("ac:task-status");
			with(new Text((isComplete)?"complete":"incomplete"));
		}
	}

	class Item extends PageElement {
		class ItemID extends PageElement {
			protected ItemID(int id) {
				super("ac:task-id");
				with(new Text("" + id));
			}
		}

		Item() {
			this(false);
		}

		Item(boolean isComplete) {
			super("ac:task");
			with(new ItemStatus(isComplete));
		}

		void setID(int i) {
			with(new ItemID(i));
		}
	}

	public TaskList() {
		super("ac:task-list");
	}

	public TaskList.ItemBody addItem(boolean isComplete) {
		Item i = new Item(isComplete);
		with(i);
		i.setID(getChildren().size());
		ItemBody b = new ItemBody();
		i.getChildren().add(b);
		return b;
	}

	/**
	 * Incomplete task item
	 * @param e
	 * @return
	 */
	public TaskList withItem(PageElement e) {
		return withItem(false, e);
	}
	public TaskList withItem(boolean isComplete,PageElement e) {
		addItem(isComplete).getChildren().add(e);
		return this;
	}

}
