package tech.asmussen.cql.misc;

/**
 * A utility class for coloring text in the console.
 *
 * @author Bastian Asmussen
 * @version 1.0.0
 * @see Color
 * @see #getColorCode(Color, boolean)
 * @see #getReset()
 * @see #colorize(String)
 */
public final class ANSI {
	
	/**
	 * An enumerator for the ANSI codes for colors.
	 *
	 * @since 1.0.0
	 */
	public enum Color {
		
		BLACK(30),
		RED(31),
		GREEN(32),
		YELLOW(33),
		BLUE(34),
		PURPLE(35),
		CYAN(36),
		WHITE(37);
		
		private final int code;
		
		Color(int code) {
			
			this.code = code;
		}
		
		int getCode() {
			
			return code;
		}
	}
	
	/**
	 * Get the ANSI code for the given color.
	 *
	 * @param color  The color to get the ANSI code of.
	 * @param isText Whether the color is for text or background.
	 * @return The ANSI code for the given color.
	 * @see Color
	 * @since 1.0.0
	 */
	private static String getColorCode(Color color, boolean isText) {
		
		final int code = color.getCode() + (isText ? 0 : 10); // Add 10 to the code if it's for the background.
		
		return "\u001B[" + code + "m";
	}
	
	/**
	 * Get the ANSI code for resetting colors.
	 *
	 * @return The ANSI code for resetting colors.
	 * @since 1.0.0
	 */
	private static String getReset() {
		
		return "\u001B[0m";
	}
	
	/**
	 * Colorizes a string.
	 *
	 * @param input The text to colorize.
	 * @return The colorized string.
	 * @see #getColorCode(Color, boolean)
	 * @see #getReset()
	 * @since 1.0.0
	 */
	public static String colorize(String input) {
		
		StringBuilder output = new StringBuilder();
		
		boolean lastWasAmpersand = false;
		
		for (char c : input.toCharArray()) {
			
			if (input.indexOf(c) == input.length() - 1) {
				
				output.append(c);
				
				break;
			}
			
			if (lastWasAmpersand) {
				
				lastWasAmpersand = false;
				
				continue;
			}
			
			if (c == '&') {
				
				lastWasAmpersand = true;
				
				final char next = input.charAt(input.indexOf(c) + 1);
				
				switch (next) {
					
					// Text colors:
					case '0' -> output.append(getColorCode(Color.BLACK,  true));
					case '1' -> output.append(getColorCode(Color.BLUE,   true));
					case '2' -> output.append(getColorCode(Color.GREEN,  true));
					case '3' -> output.append(getColorCode(Color.CYAN,   true));
					case '4' -> output.append(getColorCode(Color.RED,    true));
					case '5' -> output.append(getColorCode(Color.PURPLE, true));
					case '6' -> output.append(getColorCode(Color.YELLOW, true));
					case '7' -> output.append(getColorCode(Color.WHITE,  true));
					
					// Background colors:
					case '8' -> output.append(getColorCode(Color.BLACK,  false));
					case '9' -> output.append(getColorCode(Color.BLUE,   false));
					case 'a' -> output.append(getColorCode(Color.GREEN,  false));
					case 'b' -> output.append(getColorCode(Color.CYAN,   false));
					case 'c' -> output.append(getColorCode(Color.RED,    false));
					case 'd' -> output.append(getColorCode(Color.PURPLE, false)); // deez nuts
					case 'e' -> output.append(getColorCode(Color.YELLOW, false));
					case 'f' -> output.append(getColorCode(Color.WHITE,  false));
					
					case 'r' -> output.append(getReset());
					
					default -> {
						
						output.append(c);
						
						continue;
					}
				}
				
				output.append(getReset());
				
			} else {
				
				output.append(c);
			}
			
			System.out.println(output);
		}
		
		return output.toString();
	}
	
	public static void main(String[] args) {
		
		System.out.println(colorize("&2Hello, &4world!"));
	}
}
