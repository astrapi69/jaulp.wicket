/**
 * Copyright (C) 2010 Asterios Raptis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.alpharogroup.wicket.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;

/**
 * The class {@link FocusRequestBehavior} set the focus on a component when the page is load.
 */
public class FocusRequestBehavior extends Behavior
{

	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Constant DEFAULT_ID is the default id that will be set if the id is not set explicit.
	 */
	public static final String DEFAULT_ID = FocusRequestBehavior.class.getSimpleName();

	/**
	 * Factory method to create a new {@link FocusRequestBehavior} object for the component that is
	 * added.
	 *
	 * @return the new {@link FocusRequestBehavior} object
	 */
	public static FocusRequestBehavior of()
	{
		return new FocusRequestBehavior();
	}

	/**
	 * Factory method to create a new {@link FocusRequestBehavior} object from the given clearValue
	 * parameter for the component that is added.
	 *
	 * @param clearValue
	 *            the clear value
	 * @return the new {@link FocusRequestBehavior} object
	 */
	public static FocusRequestBehavior of(final boolean clearValue)
	{
		return new FocusRequestBehavior(clearValue);
	}

	/**
	 * Factory method to create a new {@link FocusRequestBehavior} object from the given clearValue
	 * and delay parameter for the component that is added.
	 *
	 * @param clearValue
	 *            the clear value
	 * @param delay
	 *            the delay
	 * @return the new {@link FocusRequestBehavior} object
	 */
	public static FocusRequestBehavior of(final boolean clearValue, final Integer delay)
	{
		return new FocusRequestBehavior(clearValue, delay);
	}

	/**
	 * Factory method to create a new {@link FocusRequestBehavior} object from the given delay
	 * parameter for the component that is added.
	 *
	 * @param delay
	 *            the delay
	 * @return the new {@link FocusRequestBehavior} object
	 */
	public static FocusRequestBehavior of(final Integer delay)
	{
		return new FocusRequestBehavior(delay);
	}

	/** The flag if the value may be clear. */
	private final boolean clearValue;

	/** The delay that is set in the setTimeout method. */
	private final int delay;

	/**
	 * Instantiates a new request focus behavior.
	 */
	public FocusRequestBehavior()
	{
		this(false);
	}

	/**
	 * Instantiates a new {@link FocusRequestBehavior} object.
	 *
	 * @param clearValue
	 *            The flag if the value may be clear.
	 */
	public FocusRequestBehavior(final boolean clearValue)
	{
		this(clearValue, 1);
	}

	/**
	 * Instantiates a new {@link FocusRequestBehavior} object.
	 *
	 * @param clearValue
	 *            The flag if the value may be clear.
	 * @param delay
	 *            the delay
	 */
	public FocusRequestBehavior(final boolean clearValue, final Integer delay)
	{
		this.clearValue = clearValue;
		this.delay = delay;
	}

	/**
	 * Instantiates a new {@link FocusRequestBehavior} object.
	 *
	 * @param delay
	 *            the delay
	 */
	public FocusRequestBehavior(final Integer delay)
	{
		this(false, delay);
	}

	/**
	 * Factory method that creates the java script code for request focus.
	 *
	 * @param component
	 *            the component
	 * @return the string
	 */
	protected String newJavaScript(final Component component)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("setTimeout(" + "function() {" + "var component = document.getElementById(\"")
			.append(component.getMarkupId()).append("\");");
		if (clearValue)
		{
			sb.append("component.value = \"\";");
		}
		sb.append("component.focus();");
		sb.append("component.select();");
		sb.append("}, " + this.delay + ")");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(final Component component, final IHeaderResponse response)
	{
		super.renderHead(component, response);
		component.setOutputMarkupId(true);
		response.render(OnLoadHeaderItem.forScript(newJavaScript(component)));
	}

}
