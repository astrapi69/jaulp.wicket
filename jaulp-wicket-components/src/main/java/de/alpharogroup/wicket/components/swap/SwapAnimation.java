package de.alpharogroup.wicket.components.swap;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The class {@link SwapAnimation} hold the duration for the animations.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
public class SwapAnimation implements Serializable {
	private static final long serialVersionUID = 1L;
	private int viewDuration;
	private int editDuration;
}
