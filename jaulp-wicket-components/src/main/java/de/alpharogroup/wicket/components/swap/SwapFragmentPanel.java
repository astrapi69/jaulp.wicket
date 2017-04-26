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
package de.alpharogroup.wicket.components.swap;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

import de.alpharogroup.wicket.base.BasePanel;
import de.alpharogroup.wicket.behaviors.DisplayNoneBehavior;
import lombok.Getter;

/**
 * The abstract class {@link SwapFragmentPanel} holds two Fragments that can be swapped.
 *
 * @param <T>
 *            the generic type of the model object.
 */
public abstract class SwapFragmentPanel<T> extends BasePanel<T>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The Constant logger. */
	protected static final Logger LOGGER = Logger.getLogger(SwapFragmentPanel.class.getName());

	/** The id for the placeholder where to swap the fragments. */
	private static final String FRAGMENT_ID = "fragment-placeholder";

	/** The view fragment. */
	@Getter
	private Fragment view;

	/** The edit fragment. */
	@Getter
	private Fragment edit;

	/** The swap animation. */
	@Getter
	private SwapAnimation swapAnimation;

	/** The ModeContext shows if the view mode or edit mode is visible. */
	@Getter
	private ModeContext modeContext = ModeContext.VIEW_MODE;

	/**
	 * Instantiates a new {@link SwapFragmentPanel}.
	 *
	 * @param id
	 *            the id
	 * @param model
	 *            the model
	 */
	public SwapFragmentPanel(final String id, final IModel<T> model)
	{
		super(id, Args.notNull(model, "model"));
		setOutputMarkupPlaceholderTag(true);
		add(view = newViewFragment(FRAGMENT_ID));
		edit = newEditFragment(FRAGMENT_ID);
		swapAnimation = newSwapAnimation();
	}
	
	public SwapAnimation newSwapAnimation() {
		return SwapAnimation.builder().editDuration(300).viewDuration(300).build();		
	}
    
	/**
	 * Abstract factory method for creating the new {@link Fragment} for the editable
	 * {@link Component}. This method is invoked in the constructor from the derived classes and
	 * have to be overridden so users can provide their own version of a new {@link Fragment} for
	 * the view.
	 *
	 * @param id
	 *            the id
	 * @return the new {@link Fragment} for the view.
	 */
	protected abstract Fragment newEditFragment(final String id);

	/**
	 * Factory method for creating the new {@link Fragment} for the view(not editable)
	 * {@link Component}. This method is invoked in the constructor from the derived classes and
	 * have to be overridden so users can provide their own version of a new {@link Fragment} for
	 * the view(not editable) {@link Component}.
	 *
	 * @param id
	 *            the id
	 * @return the new {@link Fragment} for the view(not editable) {@link Component}.
	 */
	protected abstract Fragment newViewFragment(final String id);


	/**
	 * Swaps from the view fragment to the edit fragment.
	 *
	 * @param target
	 *            the target
	 * @param form
	 *            the form
	 */
	public void onSwapToEdit(final AjaxRequestTarget target, final Form<?> form)
	{
		swapFragments();
		if (target != null)
		{
			view.add(new DisplayNoneBehavior());
			target.prependJavaScript("notify|jQuery('#" + view.getMarkupId() + "')." + "slideUp("
					+ swapAnimation.getViewDuration() + ", notify);");
			target.add(view);
			target.appendJavaScript(
					"jQuery('#" + view.getMarkupId() + "')." + "slideDown(" + swapAnimation.getViewDuration() + ");");
		}
		else
		{
			LOGGER.error(
				"AjaxRequestTarget is null on method SwapFragmentPanel#onSwapToEdit(AjaxRequestTarget, Form)");
		}
		modeContext = ModeContext.EDIT_MODE;
	}

	/**
	 * Swaps from the edit fragment to the view fragment.
	 *
	 * @param target
	 *            the target
	 * @param form
	 *            the form
	 */
	public void onSwapToView(final AjaxRequestTarget target, final Form<?> form)
	{
		if (target != null)
		{
			edit.add(new DisplayNoneBehavior());
			target.prependJavaScript("notify|jQuery('#" + edit.getMarkupId() + "').slideUp("
					+ swapAnimation.getEditDuration() + ", notify);");
			target.add(edit);
			target.appendJavaScript(
					"jQuery('#" + edit.getMarkupId() + "').slideDown(" + swapAnimation.getEditDuration() + ");");
    	}
		else
		{
			LOGGER.error(
				"AjaxRequestTarget is null on method SwapFragmentPanel#onSwapToView(AjaxRequestTarget, Form)");
		}
		swapFragments();
		modeContext = ModeContext.VIEW_MODE;
	}

	/**
	 * Swap the fragments.
	 */
	private void swapFragments()
	{
		final Fragment fragment = view;
		view.replaceWith(edit);
		view = edit;
		edit = fragment;
	}

	/**
	 * Swaps the fragments.
	 *
	 * @param target
	 *            the target
	 * @param form
	 *            the form
	 */
	protected void swapFragments(final AjaxRequestTarget target, final Form<?> form)
	{
		if (modeContext.equals(ModeContext.VIEW_MODE))
		{
			onSwapToEdit(target, form);
		}
		else
		{
			onSwapToView(target, form);
		}
	}

}
