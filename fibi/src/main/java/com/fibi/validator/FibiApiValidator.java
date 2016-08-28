package com.fibi.validator;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import io.swagger.annotations.ApiModelProperty;


/**
 * Validator class to validate incoming request data.
 * 
 * @author pragu
 */
public class FibiApiValidator
{
	private static final Logger LOG = Logger.getLogger(FibiApiValidator.class);

	/**
	 * Check if all required properties are present in the request object.
	 *
	 * @param requestObject
	 * @return
	 */
	public static boolean validate(final Object requestObject)
	{
		if (requestObject == null)
		{
			LOG.error(">> Validation failed: Cannot validate null");
			return false;
		}

		// get class information from object itself
		final Class<?> classType = requestObject.getClass();

		try
		{
			for (final PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(classType, Object.class)
					.getPropertyDescriptors())
			{
				// propertyEditor.getReadMethod() exposes the getter --> could be null
				final Method m = propertyDescriptor.getReadMethod();

				if (m != null && m.getAnnotation(ApiModelProperty.class) != null
						&& m.getAnnotation(ApiModelProperty.class).required())
				{
					try
					{
						if (m.invoke(requestObject) == null)
						{
							return false;
						}
					}
					catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
					{
						LOG.error(">> Validation failed: " + e);
						return false;
					}
				}
			}

			return true;
		}
		catch (final IntrospectionException e)
		{
			LOG.error(">> Validation failed: " + e);
			return false;
		}
	}
}
