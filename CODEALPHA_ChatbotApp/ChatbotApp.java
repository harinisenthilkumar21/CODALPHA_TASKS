import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class ChatbotApp {

   
    static class ChatbotCore {
        private final Map<String, String> knowledgeBase;

        
        public ChatbotCore() {
            knowledgeBase = new HashMap<>();
            
            knowledgeBase.put("hello hi hey greetings", 
                "Hello! I'm an AI assistant. How can I help you today?");
            knowledgeBase.put("your name who are you", 
                "I am ChatBot v1.0, a simple Java-based AI designed to answer common questions.");
            knowledgeBase.put("how works function", 
                "I work by analyzing keywords in your message and matching them to my knowledge base (FAQs).");
            knowledgeBase.put("java language", 
                "Java is a popular, object-oriented programming language known for its 'Write Once, Run Anywhere' principle.");
            knowledgeBase.put("programming best language", 
                "The 'best' language depends on the project! Java is great for enterprise apps, Python for data science, and JavaScript for web development.");
            knowledgeBase.put("weather today", 
                "I am not connected to the internet, so I can't check the current weather. Try asking me about programming!");
            knowledgeBase.put("thank you thanks appreciation", 
                "You're very welcome! Is there anything else I can assist you with?");
        }

       
        public String getResponse(String userInput) {
            if (userInput == null || userInput.trim().isEmpty()) {
                return "Please enter a question or a greeting.";
            }

            String normalizedInput = userInput.toLowerCase();

            
            String cleanInput = normalizedInput.replaceAll("[^a-z0-9\\s]", " ");
            
            for (Map.Entry<String, String> entry : knowledgeBase.entrySet()) {
                String[] patternTokens = entry.getKey().split("\\s+");
                
                for (String token : patternTokens) {
                    if (cleanInput.matches(".*\\b" + Pattern.quote(token) + "\\b.*")) {
                        return entry.getValue();
                    }
                }
            }

            return "I apologize, but I don't have information on that topic. Try asking me about Java or my functions!";
        }
    }


    static class ChatbotGUI extends JFrame implements ActionListener {
        private final JTextArea chatArea = new JTextArea();
        private final JTextField inputField = new JTextField();
        private final ChatbotCore botCore;

        public ChatbotGUI() {
            botCore = new ChatbotCore();

            setTitle("AI Chatbot Interface");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(600, 700);
            setLayout(new BorderLayout(10, 10)); // Use BorderLayout for structure

            chatArea.setEditable(false);
            chatArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
            chatArea.setLineWrap(true);
            chatArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(chatArea);
            scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(), "Chat History", 0, 0, new Font("Monospaced", Font.BOLD, 16)));
            add(scrollPane, BorderLayout.CENTER);

            inputField.setFont(new Font("Arial", Font.PLAIN, 16));
            inputField.addActionListener(this); 
            inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10), // Padding around the field
                inputField.getBorder()));
            
            JLabel promptLabel = new JLabel("Type your question and press Enter:", SwingConstants.LEFT);
            promptLabel.setFont(new Font("Arial", Font.BOLD, 12));

            JPanel inputPanel = new JPanel(new BorderLayout());
            inputPanel.add(promptLabel, BorderLayout.NORTH);
            inputPanel.add(inputField, BorderLayout.CENTER);

            add(inputPanel, BorderLayout.SOUTH);

            
            chatArea.append("Bot: " + botCore.getResponse("hello") + "\n\n");

            setLocationRelativeTo(null); // Center the window
            setVisible(true);
        }

       
        @Override
        public void actionPerformed(ActionEvent e) {
            String userInput = inputField.getText();
            
            if (userInput.trim().isEmpty()) return;

            chatArea.append("User: " + userInput + "\n");
            
            String botResponse = botCore.getResponse(userInput);
            
            chatArea.append("Bot: " + botResponse + "\n\n");
            
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
            inputField.setText("");
        }
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatbotGUI::new);
    }
}