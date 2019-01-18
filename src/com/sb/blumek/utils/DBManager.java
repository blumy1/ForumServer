package com.sb.blumek.utils;

import com.sb.blumek.models.*;
import com.sb.blumek.models.Thread;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class DBManager {
    private static Connection getConnection() {
        Connection connection = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("forumDB");
            connection = ds.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT U_Id, U_Username, U_Created_At, U_Image_Url, U_RId, R_Name " +
                                                                                    "FROM Users INNER JOIN Ranks ON Users.U_RId = Ranks.R_Id");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                try {
                    int id = rs.getInt("U_id");
                    String username = rs.getString("U_Username");
                    String createdAt = rs.getString("U_Created_At");
                    int rankId = rs.getInt("U_RId");
                    String rankName = rs.getString("R_Name");
                    Rank rank = new Rank(rankId, rankName);
                    String imageUrl = rs.getString("U_Image_Url");
                    User user = new User(id, username, rank, createdAt, imageUrl);
                    users.add(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static User getUser(Integer id) {
        User user = null;

        try {
            Connection connection = getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT U_Id, U_Username, U_Created_At, U_Image_Url, U_RId, R_Name " +
                                                                                    "FROM Users INNER JOIN Ranks ON Users.U_RId = Ranks.R_Id WHERE U_Id = ?");
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                try {
                    id = rs.getInt("U_id");
                    String username = rs.getString("U_Username");
                    String createdAt = rs.getString("U_Created_At");
                    int rankId = rs.getInt("U_RId");
                    String rankName = rs.getString("R_Name");
                    Rank rank = new Rank(rankId, rankName);
                    String imageUrl = rs.getString("U_Image_Url");
                    user = new User(id, username, rank, createdAt, imageUrl);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static User loginUser(User _user) {
        User user = null;

        try {
            Connection connection = getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT U_Id, U_Username, U_Created_At, U_Image_Url, U_RId, R_Name " +
                                                                                    "FROM Users INNER JOIN Ranks ON Users.U_RId = Ranks.R_Id " +
                                                                                    "WHERE U_Username=? AND U_Password=?");
            preparedStatement.setString(1, _user.getUsername());
            preparedStatement.setString(2, _user.getPassword());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                try {
                    int id = rs.getInt("U_id");
                    String username = rs.getString("U_Username");
                    String createdAt = rs.getString("U_Created_At");
                    int rankId = rs.getInt("U_RId");
                    String rankName = rs.getString("R_Name");
                    Rank rank = new Rank(rankId, rankName);
                    String imageUrl = rs.getString("U_Image_Url");
                    user = new User(id, username, rank, createdAt, imageUrl);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static void createUser(User user) {
        String insert = "INSERT INTO Users(U_Username, U_Password, U_Email, U_RId, U_Created_At, U_Image_Url)"
                        + "VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = getConnection();
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt 	(4, 3);
            preparedStatement.setString	(5, new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
            preparedStatement.setString (6, null);

            preparedStatement.execute();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Thread> getThreads() {
        List<Thread> threads = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT T_Id, T_Title, T_Created_At, T_Status, U_id, U_Username, U_Created_At, U_Image_Url, R_Id, R_Name, U_Image_Url, S_Id, S_Name " +
                                                                                    "FROM Threads INNER JOIN Users ON Threads.T_UId = Users.U_Id " +
                                                                                    "INNER JOIN Subcategories ON Subcategories.S_Id = Threads.T_SId " +
                                                                                    "INNER JOIN Ranks ON Ranks.R_Id = Users.U_RId;");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                try {
                    // Thread data
                    int threadId = rs.getInt("T_Id");
                    String threadTitle = rs.getString("T_Title");
                    String threadCreatedAt = rs.getString("T_Created_At");
                    int threadStatus = rs.getInt("T_Status");

                    // User data
                    int userId = rs.getInt("U_Id");
                    String username = rs.getString("U_Username");
                    String userCreatedAt = rs.getString("U_Created_At");
                    String userImageUrl = rs.getString("U_Image_Url");

                    // User's rank data
                    int rankId = rs.getInt("R_Id");
                    String rankName = rs.getString("R_Name");

                    // Subcategory data
                    int subcategoryId = rs.getInt("S_Id");
                    String subcategoryName = rs.getString("S_Name");

                    Rank rank = new Rank(rankId, rankName);
                    User user = new User(userId, username, rank, userCreatedAt, userImageUrl);
                    Subcategory subcategory = new Subcategory(subcategoryId, subcategoryName);
                    Thread thread = new Thread(threadId, threadTitle, user, subcategory, threadCreatedAt, threadStatus);
                    threads.add(thread);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return threads;
    }

    public static Thread getThread(Integer threadId) {
        Thread thread = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT T_Id, T_Title, T_Slug, T_Created_At, T_Status, U_Id, U_Username, U_Created_At, U_Image_Url, R_Id, R_Name, U_Image_Url, S_Id, S_Name " +
                                                                                    "FROM Threads INNER JOIN Users ON Threads.T_UId = Users.U_Id " +
                                                                                    "INNER JOIN Subcategories ON Subcategories.S_Id = Threads.T_SId " +
                                                                                    "INNER JOIN Ranks ON Ranks.R_Id = Users.U_RId WHERE Threads.T_Id = ?;");
            preparedStatement.setInt(1, threadId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                try {
                    // Thread data
                    threadId = rs.getInt("T_Id");
                    String threadTitle = rs.getString("T_Title");
                    String threadSlug = rs.getString("T_Slug");
                    String threadCreatedAt = rs.getString("T_Created_At");
                    int threadStatus = rs.getInt("T_Status");

                    // User data
                    int userId = rs.getInt("U_Id");
                    String username = rs.getString("U_Username");
                    String userCreatedAt = rs.getString("U_Created_At");
                    String userImageUrl = rs.getString("U_Image_Url");

                    // User's rank data
                    int rankId = rs.getInt("R_Id");
                    String rankName = rs.getString("R_Name");

                    // Subcategory data
                    int subcategoryId = rs.getInt("S_Id");
                    String subcategoryName = rs.getString("S_Name");

                    Rank rank = new Rank(rankId, rankName);
                    User user = new User(userId, username, rank, userCreatedAt, userImageUrl);
                    Subcategory subcategory = new Subcategory(subcategoryId, subcategoryName);
                    thread = new Thread(threadId, threadTitle, threadSlug, user, subcategory, threadCreatedAt, threadStatus);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return thread;
    }

    public static Thread getLastThread() {
        Thread thread = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT T_Id, T_Title, T_Slug, T_Created_At, T_Status, U_Id, U_Username, U_Created_At, U_Image_Url, R_Id, R_Name, U_Image_Url, S_Id, S_Name " +
                                                                                    "FROM Threads INNER JOIN Users ON Threads.T_UId = Users.U_Id " +
                                                                                    "INNER JOIN Subcategories ON Subcategories.S_Id = Threads.T_SId " +
                                                                                    "INNER JOIN Ranks ON Ranks.R_Id = Users.U_RId ORDER BY T_Created_At DESC LIMIT 1");

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                try {
                    // Thread data
                    int threadId = rs.getInt("T_Id");
                    String threadSlug = rs.getString("T_Slug");
                    String threadTitle = rs.getString("T_Title");
                    String threadCreatedAt = rs.getString("T_Created_At");
                    int threadStatus = rs.getInt("T_Status");

                    // User data
                    int userId = rs.getInt("U_Id");
                    String username = rs.getString("U_Username");
                    String userCreatedAt = rs.getString("U_Created_At");
                    String userImageUrl = rs.getString("U_Image_Url");

                    // User's rank data
                    int rankId = rs.getInt("R_Id");
                    String rankName = rs.getString("R_Name");

                    // Subcategory data
                    int subcategoryId = rs.getInt("S_Id");
                    String subcategoryName = rs.getString("S_Name");

                    Rank rank = new Rank(rankId, rankName);
                    User user = new User(userId, username, rank, userCreatedAt, userImageUrl);
                    Subcategory subcategory = new Subcategory(subcategoryId, subcategoryName);
                    thread = new Thread(threadId, threadTitle, threadSlug, user, subcategory, threadCreatedAt, threadStatus);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return thread;
    }

    public static Thread createThread(Thread thread) {
        String insert = "INSERT INTO Threads(T_Title, T_Slug, T_UId, T_SId, T_Created_At, T_Status)"
                        + "VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = getConnection();
        PreparedStatement preparedStatement;

        int newId = 0;

        try {
            preparedStatement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, thread.getTitle());

            String slug = Slug.makeSlug(thread.getTitle());
            preparedStatement.setString(2, slug);

            preparedStatement.setInt(3, thread.getAuthor().getId());
            preparedStatement.setInt(4, thread.getSubcategory().getId());
            preparedStatement.setString(5, new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
            preparedStatement.setInt(6, 1);

            preparedStatement.executeUpdate();

            ResultSet rs= preparedStatement.getGeneratedKeys();
            if(rs.next()){
                newId = rs.getInt(1);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getThread(newId);
    }

    public static List<Post> getThreadPosts(Integer threadId) {
        List<Post> posts = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT P_Id, T_Id, P_Text, P_Created_At, T_Title, T_Created_At, U_Id, U_Username, U_Created_At, U_Image_Url, R_Id, R_Name FROM Posts INNER JOIN Threads ON Posts.P_TId = Threads.T_Id \n" +
                                                                                    "INNER JOIN Users ON Posts.P_UId = Users.U_Id \n" +
                                                                                    "INNER JOIN Subcategories ON Subcategories.S_Id = Threads.T_SId \n" +
                                                                                    "INNER JOIN Ranks ON Ranks.R_Id = Users.U_RId\n" +
                                                                                    "WHERE Threads.T_Id = ?");
            preparedStatement.setInt(1, threadId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                try {
                    // Post data
                    int postId = rs.getInt("P_Id");
                    String postText = rs.getString("P_Text");
                    String postCreatedAt = rs.getString("P_Created_At");

                    // Thread data
                    threadId = rs.getInt("T_Id");
                    String threadTitle = rs.getString("T_Title");
                    String threadCreatedAt = rs.getString("T_Created_At");

                    // User data
                    int userId = rs.getInt("U_Id");
                    String username = rs.getString("U_Username");
                    String userCreatedAt = rs.getString("U_Created_At");
                    String userImageUrl = rs.getString("U_Image_Url");

                    // User's rank data
                    int rankId = rs.getInt("R_Id");
                    String rankName = rs.getString("R_Name");


                    Rank rank = new Rank(rankId, rankName);
                    User user = new User(userId, username, rank, userCreatedAt, userImageUrl);
                    Thread thread = new Thread(threadId, threadTitle, threadCreatedAt);
                    Post post = new Post(postId, user, thread, postText, postCreatedAt);
                    posts.add(post);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    public static Post getThreadPost(Integer threadId, Integer postId) {
        Post post = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT P_Id, T_Id, P_Text, P_Created_At, T_Title, T_Created_At, U_Id, U_Username, U_Created_At, U_Image_Url, R_Id, R_Name FROM Posts INNER JOIN Threads ON Posts.P_TId = Threads.T_Id \n" +
                                                                                    "INNER JOIN Users ON Posts.P_UId = Users.U_Id \n" +
                                                                                    "INNER JOIN Subcategories ON Subcategories.S_Id = Threads.T_SId \n" +
                                                                                    "INNER JOIN Ranks ON Ranks.R_Id = Users.U_RId\n" +
                                                                                    "WHERE Threads.T_Id = ? AND Posts.P_Id = ?");
            preparedStatement.setInt(1, threadId);
            preparedStatement.setInt(2, postId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                try {
                    // Post data
                    postId = rs.getInt("P_Id");
                    String postText = rs.getString("P_Text");
                    String postCreatedAt = rs.getString("P_Created_At");

                    // Thread data
                    threadId = rs.getInt("T_Id");
                    String threadTitle = rs.getString("T_Title");
                    String threadCreatedAt = rs.getString("T_Created_At");

                    // User data
                    int userId = rs.getInt("U_Id");
                    String username = rs.getString("U_Username");
                    String userCreatedAt = rs.getString("U_Created_At");
                    String userImageUrl = rs.getString("U_Image_Url");

                    // User's rank data
                    int rankId = rs.getInt("R_Id");
                    String rankName = rs.getString("R_Name");


                    Rank rank = new Rank(rankId, rankName);
                    User user = new User(userId, username, rank, userCreatedAt, userImageUrl);
                    Thread thread = new Thread(threadId, threadTitle, threadCreatedAt);
                    post = new Post(postId, user, thread, postText, postCreatedAt);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }

    public static Post getLastThreadPost(Integer threadId) {
        Post post = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT P_Id, T_Id, P_Text, P_Created_At, T_Title, T_Created_At, U_Id, U_Username, U_Created_At, U_Image_Url, R_Id, R_Name " +
                                                                                    "FROM Posts INNER JOIN Threads ON Posts.P_TId = Threads.T_Id " +
                                                                                    "INNER JOIN Users ON Posts.P_UId = Users.U_Id " +
                                                                                    "INNER JOIN Subcategories ON Subcategories.S_Id = Threads.T_SId " +
                                                                                    "INNER JOIN Ranks ON Ranks.R_Id = Users.U_RId " +
                                                                                    "WHERE Threads.T_Id = ?" +
                                                                                    "ORDER BY Posts.P_Created_At DESC");
            preparedStatement.setInt(1, threadId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                try {
                    // Post data
                    int postId = rs.getInt("P_Id");
                    String postText = rs.getString("P_Text");
                    String postCreatedAt = rs.getString("P_Created_At");

                    // Thread data
                    threadId = rs.getInt("T_Id");
                    String threadTitle = rs.getString("T_Title");
                    String threadCreatedAt = rs.getString("T_Created_At");

                    // User data
                    int userId = rs.getInt("U_Id");
                    String username = rs.getString("U_Username");
                    String userCreatedAt = rs.getString("U_Created_At");
                    String userImageUrl = rs.getString("U_Image_Url");

                    // User's rank data
                    int rankId = rs.getInt("R_Id");
                    String rankName = rs.getString("R_Name");


                    Rank rank = new Rank(rankId, rankName);
                    User user = new User(userId, username, rank, userCreatedAt, userImageUrl);
                    Thread thread = new Thread(threadId, threadTitle, threadCreatedAt);
                    post = new Post(postId, user, thread, postText, postCreatedAt);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }

    public static void createThreadPost(Integer threadId, Post post) {
        String insert = "INSERT INTO Posts(P_UId, P_TId, P_Text, P_Created_At)"
                        + "VALUES (?, ?, ?, ?)";

        Connection connection = getConnection();
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1, post.getAuthor().getId());
            preparedStatement.setInt(2, threadId);
            preparedStatement.setString(3, post.getText());
            preparedStatement.setString(4, new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));

            preparedStatement.execute();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Categories");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                try {
                    // Category data
                    int categoryId = rs.getInt("C_Id");
                    String categoryName = rs.getString("C_Name");

                    Category category = new Category(categoryId, categoryName);
                    categories.add(category);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public static Category getCategory(Integer categoryId) {
        Category category = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Categories Where C_Id = ?");
            preparedStatement.setInt(1, categoryId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                try {
                    // Category data
                    categoryId = rs.getInt("C_Id");
                    String categoryName = rs.getString("C_Name");

                    category = new Category(categoryId, categoryName);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    public static void createCategory(Category category) {
        String insert = "INSERT INTO Categories(C_Name)"
                        + "VALUES (?)";

        Connection connection = getConnection();
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, category.getName());

            preparedStatement.execute();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Subcategory> getCategorySubcategories(Integer categoryId) {
        List<Subcategory> subcategories = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT (SELECT COUNT(*) FROM Threads WHERE Threads.T_SId = Subcategories.S_Id) as Threads_Amount, " +
                                                                                    "(SELECT COUNT(*) FROM Threads INNER JOIN Posts ON Posts.P_TId = Threads.T_Id WHERE Threads.T_SId = Subcategories.S_Id) as Posts_Amount, " +
                                                                                    "S_Id, S_Name, S_Slug, C_Id, C_Name FROM Subcategories " +
                                                                                    "INNER JOIN Categories ON Subcategories.S_CId = Categories.C_Id WHERE Categories.C_Id = ?");
            preparedStatement.setInt(1, categoryId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                try {
                    // Category data
                    categoryId = rs.getInt("C_Id");
                    String categoryName = rs.getString("C_Name");

                    // Subcategory
                    int subcategoryId = rs.getInt("S_Id");
                    String subcategoryName = rs.getString("S_Name");
                    String subcategorySlug = rs.getString("S_Slug");
                    int threadsAmount  = rs.getInt("Threads_Amount");
                    int postsAmount  = rs.getInt("Posts_Amount");

                    Category category = new Category(categoryId, categoryName);
                    Subcategory subcategory = new Subcategory(subcategoryId, subcategoryName, subcategorySlug, category, threadsAmount, postsAmount);
                    subcategories.add(subcategory);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subcategories;
    }

    public static Subcategory getSubcategory(Integer subcategoryId) {
        Subcategory subcategory = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT (SELECT COUNT(*) FROM Threads WHERE Threads.T_SId = Subcategories.S_Id) as Threads_Amount, " +
                                                                                    "(SELECT COUNT(*) FROM Threads INNER JOIN Posts ON Posts.P_TId = Threads.T_Id WHERE Threads.T_SId = Subcategories.S_Id) as Posts_Amount, " +
                                                                                    "S_Id, S_Name, S_Slug, C_Id, C_Name FROM Subcategories \n" +
                                                                                    "INNER JOIN Categories ON Subcategories.S_CId = Categories.C_Id WHERE Subcategories.S_Id = ?");
            preparedStatement.setInt(1, subcategoryId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                try {
                    // Category data
                    int categoryId = rs.getInt("C_Id");
                    String categoryName = rs.getString("C_Name");

                    // Subcategory
                    subcategoryId = rs.getInt("S_Id");
                    String subcategoryName = rs.getString("S_Name");
                    String subcategorySlug = rs.getString("S_Slug");
                    int threadsAmount  = rs.getInt("Threads_Amount");
                    int postsAmount  = rs.getInt("Posts_Amount");

                    Category category = new Category(categoryId, categoryName);
                    subcategory = new Subcategory(subcategoryId, subcategoryName, subcategorySlug, category, threadsAmount, postsAmount);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subcategory;
    }

    public static Post getLastSubcategoryPost(Integer categoryId, Integer subcategoryId) {
        Post post = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT P_Id, T_Id, P_Text, P_Created_At, T_Title, T_Created_At, U_Id, U_Username, U_Created_At, U_Image_Url, R_Id, R_Name " +
                                                                                    "FROM Posts INNER JOIN Threads ON Posts.P_TId = Threads.T_Id " +
                                                                                    "INNER JOIN Users ON Users.U_Id = Posts.P_UId " +
                                                                                    "INNER JOIN Ranks ON Ranks.R_Id = Users.U_RId " +
                                                                                    "INNER JOIN Subcategories ON Subcategories.S_Id = Threads.T_SId " +
                                                                                    "INNER JOIN Categories ON Categories.C_Id = Subcategories.S_CId " +
                                                                                    "WHERE Categories.C_Id = ? AND Subcategories.S_Id = ? " +
                                                                                    "ORDER BY Posts.P_Id DESC LIMIT 1");
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, subcategoryId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                try {
                    // Post data
                    int postId = rs.getInt("P_Id");
                    String postText = rs.getString("P_Text");
                    String postCreatedAt = rs.getString("P_Created_At");

                    // Thread data
                    int threadId = rs.getInt("T_Id");
                    String threadTitle = rs.getString("T_Title");
                    String threadCreatedAt = rs.getString("T_Created_At");

                    // User data
                    int userId = rs.getInt("U_Id");
                    String username = rs.getString("U_Username");
                    String userCreatedAt = rs.getString("U_Created_At");
                    String userImageUrl = rs.getString("U_Image_Url");

                    // User's rank data
                    int rankId = rs.getInt("R_Id");
                    String rankName = rs.getString("R_Name");


                    Rank rank = new Rank(rankId, rankName);
                    User user = new User(userId, username, rank, userCreatedAt, userImageUrl);
                    Thread thread = new Thread(threadId, threadTitle, threadCreatedAt);
                    post = new Post(postId, user, thread, postText, postCreatedAt);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }

    public static List<Thread> getSubcategoryThreads(Integer subcategoryId) {
        List<Thread> threads = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT T_Id, T_Title, T_Slug, T_Created_At, T_Status, U_id, U_Username, U_Created_At, U_Image_Url, R_Id, R_Name, U_Image_Url, S_Id, S_Name " +
                                                                                    "FROM Threads INNER JOIN Users ON Threads.T_UId = Users.U_Id " +
                                                                                    "INNER JOIN Subcategories ON Subcategories.S_Id = Threads.T_SId " +
                                                                                    "INNER JOIN Ranks ON Ranks.R_Id = Users.U_RId WHERE  Subcategories.S_Id = ?");
            preparedStatement.setInt(1, subcategoryId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                try {
                    // Thread data
                    int threadId = rs.getInt("T_Id");
                    String threadTitle = rs.getString("T_Title");
                    String threadSlug = rs.getString("T_Slug");
                    String threadCreatedAt = rs.getString("T_Created_At");
                    int threadStatus = rs.getInt("T_Status");

                    // User data
                    int userId = rs.getInt("U_Id");
                    String username = rs.getString("U_Username");
                    String userCreatedAt = rs.getString("U_Created_At");
                    String userImageUrl = rs.getString("U_Image_Url");

                    // User's rank data
                    int rankId = rs.getInt("R_Id");
                    String rankName = rs.getString("R_Name");

                    // Subcategory data
                    subcategoryId = rs.getInt("S_Id");
                    String subcategoryName = rs.getString("S_Name");

                    Rank rank = new Rank(rankId, rankName);
                    User user = new User(userId, username, rank, userCreatedAt, userImageUrl);
                    Subcategory subcategory = new Subcategory(subcategoryId, subcategoryName);
                    Thread thread = new Thread(threadId, threadTitle, threadSlug, user, subcategory, threadCreatedAt, threadStatus);
                    threads.add(thread);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return threads;
    }

    public static void createCategorySubcategory(Integer categoryId, Subcategory subcategory) {
        String insert = "INSERT INTO Subcategories(S_CId, S_Name, S_Slug)"
                        + "VALUES (?, ?, ?)";

        Connection connection = getConnection();
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setString(2, subcategory.getName());

            String slug = Slug.makeSlug(subcategory.getName());

            preparedStatement.setString(3, slug);

            preparedStatement.execute();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
