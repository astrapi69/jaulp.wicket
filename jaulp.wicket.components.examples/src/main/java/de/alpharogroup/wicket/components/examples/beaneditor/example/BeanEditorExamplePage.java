package de.alpharogroup.wicket.components.examples.beaneditor.example;

import de.alpharogroup.wicket.components.examples.beaneditor.Customer;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.alpharogroup.wicket.components.examples.area.publicly.PubliclyBasePage;


public class BeanEditorExamplePage extends PubliclyBasePage<Customer> {
  private static final long serialVersionUID = 1L;

  public BeanEditorExamplePage(IModel<Customer> model)
  {
    super(model);
  }

  public BeanEditorExamplePage(final PageParameters parameters)
  {
    super(parameters);
  }

  @Override
  public Component getContainerPanel()
  {
    IModel<Customer> model = getModel();
    if(model == null) {
      Customer customer = new Customer();
      model = Model.of(customer);
    }
    return new BeanEditorExamplePanel(CONTAINER_PANEL_ID, model);
  }


}
