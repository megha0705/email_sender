# Spring Boot Email Sender

## Project Structure
```bash
src/main/java/com/example/email/
│
├── EmailController        # REST controller to trigger email scheduling
├── EmailLogService        # Logs all email sending statuses to a file
├── EmailModel             # Model for storing email addresses
├── EmailSchedular         # Scheduler that sends one email every 90 seconds
├── EmailSenderApplication # Main Spring Boot application entry point
├── EmailService           # Core service for sending emails
├── EmailStatus            # Enum for SUCCESSFUL, FAILED, INVALID, DUPLICATE
├── FileReader             # Reads email list from CSV and validates them
├── MessageModel           # Holds the subject & body of the email
```
## Installation set up 
📄 CSV Format
The emails.csv file must contain a column named email:
```bash
email
john@example.com
jane@example.com
test@example.com
```
1️⃣ Clone the repository
```bash
git clone https://github.com/megha0705/email_sender.git
cd email_sender
```
2️⃣ Configure SMTP in application.properties
```application.properties
spring.mail.host=smtp.yourprovider.com
spring.mail.port=587
spring.mail.username=your_email@example.com
spring.mail.password=your_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
3️⃣ Build & Run
```bash
mvn clean install
mvn spring-boot:run
```
