package calculator;

public class Command {
    private final String command;

    public Command(String input) throws IllegalArgumentException {
        String spaceLessInput = input.replaceAll("\\s+", "");
        this.command = this.checkCommand(spaceLessInput);
    }

    private String checkCommand(String input) throws IllegalArgumentException {
        if (input.equals("/exit") || input.equals("/help")) {
            return input;
        } else {
            throw new IllegalArgumentException("Unknown command");
        }
    }

    public boolean execute() {
        return switch (this.command) {
            case "/exit" -> {
                System.out.println("Bye!");
                yield true;
            }
            case "/help" -> {
                System.out.println("Calculator help");
                yield false;
            }
            default -> false;
        };
    }
}
