public class HelloWorld {
    public String getHelloWorld(String[] message) {
        String result;
        int message_length = message.length;

        switch (message_length) {
            case 1:
                result = "Olá Mundo, " + message[0] + " tudo bem?";
                break;
            case 2:
                result = "Olá Mundo, " + message[0] + " " + message[1];
                break;
            default:
                result = "Olá Mundo";
                break;
        }

        return result;
    }
}
