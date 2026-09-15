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

---

