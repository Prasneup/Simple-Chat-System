import java.util.*;

class Message {
    private String sender;
    private String receiver;
    private String content;
    private Date timestamp;

    public Message(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = new Date();
    }

    public void display() {
        System.out.println("📩 From: " + sender + " | 🕒 At: " + timestamp);
        System.out.println("💬 Message: " + content);
        System.out.println("──────────────────────────────");
    }

    public String getReceiver() { return receiver; }
    public String getSender() { return sender; }
}

class User {
    private String username;
    private ArrayList<Message> inbox = new ArrayList<>();
    private ArrayList<Message> sentbox = new ArrayList<>();

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void receiveMessage(Message msg) {
        inbox.add(msg);
    }

    public void sendMessage(User receiver, String content) {
        Message msg = new Message(this.username, receiver.getUsername(), content);
        receiver.receiveMessage(msg);
        sentbox.add(msg);
    }

    public void showInbox() {
        System.out.println("\n📥 Inbox of " + username);
        if (inbox.isEmpty()) {
            System.out.println("No messages yet!");
        } else {
            inbox.forEach(Message::display);
        }
    }

    public void showSentbox() {
        System.out.println("\n📤 Sent Messages from " + username);
        if (sentbox.isEmpty()) {
            System.out.println("You haven’t sent anything yet!");
        } else {
            sentbox.forEach(Message::display);
        }
    }

    public void deleteAllMessages() {
        inbox.clear();
        sentbox.clear();
        System.out.println("🗑️ All messages cleared for user: " + username);
    }
}

public class ChatVerse {
    private static HashMap<String, User> users = new HashMap<>();
    private static Scanner sc = new Scanner(System.in);
    private static Random rand = new Random();

    public static void main(String[] args) {
        int choice;
        printBanner();
        do {
            menu();
            choice = sc.nextInt();
            sc.nextLine(); // flush

            switch (choice) {
                case 1 -> createUser();
                case 2 -> sendMessage();
                case 3 -> viewInbox();
                case 4 -> viewSentbox();
                case 5 -> listUsers();
                case 6 -> deleteMessages();
                case 7 -> System.out.println("👋 Bye! Thanks for chatting on ChatVerse.");
                default -> System.out.println("⚠️ Invalid choice, try again!");
            }
        } while (choice != 7);
    }

    static void printBanner() {
        System.out.println("═════════════════════════════════════");
        System.out.println("🪐 Welcome to ChatVerse - Java Chat App!");
        System.out.println("═════════════════════════════════════");
    }

    static void menu() {
        System.out.println("\n📘 Menu:");
        System.out.println("1. Create User");
        System.out.println("2. Send Message");
        System.out.println("3. View Inbox");
        System.out.println("4. View Sent Messages");
        System.out.println("5. View All Users");
        System.out.println("6. Delete All Messages");
        System.out.println("7. Exit");
        System.out.print("Choose your action: ");
    }

    static void createUser() {
        System.out.print("Enter new username: ");
        String name = sc.nextLine();
        if (users.containsKey(name)) {
            System.out.println("❌ User already exists.");
        } else {
            users.put(name, new User(name));
            System.out.println("✅ User created: " + name);
        }
    }

    static void sendMessage() {
        System.out.print("Sender: ");
        String sender = sc.nextLine();
        System.out.print("Receiver: ");
        String receiver = sc.nextLine();

        if (!users.containsKey(sender) || !users.containsKey(receiver)) {
            System.out.println("❌ Both users must exist.");
            return;
        }

        System.out.print("Type your message: ");
        String msg = sc.nextLine() + " " + randomEmoji();

        users.get(sender).sendMessage(users.get(receiver), msg);
        System.out.println("✅ Message sent!");
    }

    static void viewInbox() {
        System.out.print("Enter username: ");
        String name = sc.nextLine();
        if (users.containsKey(name)) {
            users.get(name).showInbox();
        } else {
            System.out.println("❌ User does not exist.");
        }
    }

    static void viewSentbox() {
        System.out.print("Enter username: ");
        String name = sc.nextLine();
        if (users.containsKey(name)) {
            users.get(name).showSentbox();
        } else {
            System.out.println("❌ User does not exist.");
        }
    }

    static void listUsers() {
        System.out.println("\n👥 Registered Users:");
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (String name : users.keySet()) {
                System.out.println("🔹 " + name);
            }
        }
    }

    static void deleteMessages() {
        System.out.print("Enter username to clear messages: ");
        String name = sc.nextLine();
        if (users.containsKey(name)) {
            users.get(name).deleteAllMessages();
        } else {
            System.out.println("❌ User not found.");
        }
    }

    static String randomEmoji() {
        String[] emojis = {"😀", "🔥", "❤️", "🌟", "👀", "😎", "🚀", "🥳"};
        return emojis[rand.nextInt(emojis.length)];
    }
}

