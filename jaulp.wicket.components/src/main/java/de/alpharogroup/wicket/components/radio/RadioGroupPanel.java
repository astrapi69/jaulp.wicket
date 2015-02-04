package de.alpharogroup.wicket.components.radio;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.jaulp.wicket.base.BasePanel;

public abstract class RadioGroupPanel<T> extends BasePanel<RadioGroupModel<T>>
{
	private static final long serialVersionUID = 1L;

	public RadioGroupPanel(String id, final IModel<RadioGroupModel<T>> model)
	{
		super(id, model);
		setOutputMarkupId(true);
		Form<?> form = new Form<>("form");
		add(form);
		final RadioGroup<T> group = new RadioGroup<T>("group", new PropertyModel<T>(
			model.getObject(), "selected"));
		group.add(new AjaxFormChoiceComponentUpdatingBehavior()
		{
			private static final long serialVersionUID = 1L;

			protected void onUpdate(AjaxRequestTarget target)
			{
				RadioGroupPanel.this.onUpdate(target);
			}
		});
		group.setOutputMarkupId(true);
		form.add(group);

		group.add(new ListView<T>("choice", model.getObject().getRadios())
		{
			/**
			 * The serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			protected void populateItem(final ListItem<T> item)
			{
				Radio<T> radio = new Radio<T>("radio", item.getModel(), group);
				radio.setOutputMarkupId(true);
				radio.add(new AttributeAppender("name", newRadioName()));
				item.add(radio);
				item.add(RadioGroupPanel.this.newLabel("label", radio.getMarkupId(),
					item.getModel()));
			}
		}.setOutputMarkupId(true));
	}

	/**
	 * Factory method for creating a new Label with the for attribute.
	 * 
	 * @param id
	 *            the id
	 * @param forId
	 *            the for id
	 * @param model
	 *            the list item model
	 * @return the label
	 */
	protected Label newLabel(String id, String forId, IModel<T> model)
	{
		return null;
	};

	protected void onUpdate(AjaxRequestTarget target)
	{
		System.out.println("onupdate...");
	}

	protected String newRadioName()
	{
		return "radiogroup";
	}

}