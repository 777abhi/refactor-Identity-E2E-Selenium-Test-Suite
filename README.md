# Running Java Selenium Tests

## Prerequisites

- Java Development Kit (JDK) installed
- Apache Maven installed
- Selenium WebDriver dependencies added to `pom.xml`

## Steps to Run Tests

1. **Clone the repository:**

    ```sh
    git clone <repository-url>
    cd <repository-directory>
    ```

2. **Navigate to the project directory:**

    ```sh
    cd /path/to/your/project
    ```

3. **Ensure all dependencies are installed:**

    ```sh
    mvn clean install
    ```

4. **Run the Selenium tests:**

    ```sh
    mvn test
    ```
