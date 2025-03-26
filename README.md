# 🍳 Cook Time — Server Backend

## 🧠 Overview

This repository contains the backend implementation for Cook Time, a social network web application for chefs, amateur cooks, and restaurants to share and discover recipes.

Users can follow each other, explore new dishes, post recipes, and engage with other members in a friendly gastronomic community.

---

## 🔧 Server Description

This backend was built using a RESTful API architecture and handles:

- Recipe management and storage
- User authentication and profile control
- Following/unfollowing functionality
- Sorted data delivery (recent posts, popular users, etc.)

The system uses:

- **Linked lists**, **data trees**, and **sorting algorithms**
- JSON-based communication between client and server
- A lightweight and efficient in-memory data structure management
- Custom implementation of server logic without relying on external frameworks

---

## 🔁 Communication Logic

The server receives and responds to JSON-based queries from the client. Core operations include:

- `POST` and `GET` for users and recipes
- Login and registration validation
- Updating recipes or user data
- Delivering feeds of sorted content
