package com.urlshortener.db;

import com.urlshortener.model.UrlMapping;

import java.sql.*;
import java.util.Optional;

public class UrlRepository {

    public void save(UrlMapping mapping) {
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO urls (short_code, original_url, username) VALUES (?, ?, ?)"
            );
            ps.setString(1, mapping.getShortCode());
            ps.setString(2, mapping.getOriginalUrl());
            ps.setString(3, mapping.getUsername());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<UrlMapping> findByShortCode(String code) {
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM urls WHERE short_code = ?");
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new UrlMapping(
                    rs.getString("short_code"),
                    rs.getString("original_url"),
                    rs.getString("username")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
