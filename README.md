# Secure User Authentication & Multi-Cipher Data Encryption System

A Java Servlet-based web application providing a responsive user authentication system (Login & Sign-Up) integrated with **field-level cryptographic encryption** before database persistence.

---

## Overview

Standard authentication implementations often store user sensitive personal identifiable information (PII) in plaintext or rely solely on transport-layer encryption. This project demonstrates an end-to-end multi-cipher architecture where each individual input field (mobile number, email, address components, username, and password) is encrypted using a **distinct cryptographic algorithm** prior to inserting records into an Apache Derby database.

The application presents a clean, responsive UI built with Bootstrap 5 and handles backend business logic, validation, encryption, and decryption routines via Java Servlets and JSP.

---

## Key Features

* **Multi-Cipher Security Layer:** Applies 7 different classical cryptographic algorithms tailored to specific data types (digits, text, fixed-length strings).
* **Responsive Frontend UI:** Built with HTML5, CSS3, JavaScript, and Bootstrap 5 for seamless cross-device compatibility.
* **Input Validation:** Client-side and server-side password matching and field verification.
* **Field-Level Database Persistence:** Sensitive PII is encrypted in memory before JDBC persistence into an Apache Derby database.
* **Authenticated Dashboard (`employee.jsp`):** Validates session requests, queries the encrypted database, and decrypts record attributes dynamically for authorized display.

---

## Encryption Architecture

Each user detail collected during registration undergoes a specific cipher operation before SQL execution:

| Form Field | Data Type | Cryptographic Algorithm | Mechanism / Key |
| :--- | :--- | :--- | :--- |
| **Mobile Number** | String (Digits) | **Caesar Cipher** | Key = 3 (wraps numeric digits 0–9) |
| **Email Address** | String (Text) | **Monoalphabetic Cipher** | Custom alphabet key (`qwertyuiopasdfghjklzxcvbnm`) |
| **Username** | String (Text) | **Vernam Cipher** | XOR operation with key (`secretkey`) |
| **Password** | String (Text) | **Simple Columnar Transposition** | Key permutation (`3124`) |
| **Street Address**| String (Text) | **Polyalphabetic Cipher** | Key (`polykey`) |
| **City** | String (Text) | **Rail Fence Cipher** | 3 Rails |
| **Pincode** | Integer | **Modified Caesar Cipher** | Key = 2 (converted back to integer) |
| **State / Name** | String | Plaintext / Unencrypted | Direct storage for query performance |

---

## Tech Stack

* **Frontend:** HTML5, CSS3, JavaScript (ES6), Bootstrap 5.3
* **Backend:** Java Servlets (`javax.servlet`), JSP (JavaServer Pages)
* **Database:** Apache Derby (Java DB) via JDBC Connection Pool
* **Application Server:** Apache Tomcat 9+ / GlassFish Server
* **IDE:** NetBeans / Eclipse / Visual Studio Code

<img width="805" height="293" alt="image" src="https://github.com/user-attachments/assets/4b6563f9-c1a5-4741-84bd-6178e7c75b6d" />
<img width="842" height="390" alt="image" src="https://github.com/user-attachments/assets/4bc50218-7ce8-45d8-883e-2bd887af3d31" />
<img width="820" height="354" alt="image" src="https://github.com/user-attachments/assets/93c7c7e5-469b-4f3b-ab79-87d186ce392d" />
<img width="836" height="480" alt="image" src="https://github.com/user-attachments/assets/488a371f-092d-4ef7-987b-d1812efcc5ba" />
<img width="853" height="131" alt="image" src="https://github.com/user-attachments/assets/f485ccd6-4034-4fb8-9334-3f170156b660" />
<img width="520" height="280" alt="image" src="https://github.com/user-attachments/assets/240a25a4-0932-47a1-aba3-303000d7ee74" />
<img width="532" height="488" alt="image" src="https://github.com/user-attachments/assets/5b2295c8-5f05-4728-8ad9-b51d4fab9751" />

---

