package com.example.messageapp.ui.completeprofile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.messageapp.R;
import com.example.messageapp.ui.CompleteProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AvatarFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avatar, container, false);

        ImageView ivAvatar = view.findViewById(R.id.ivAvatar);
        ivAvatar.setOnClickListener(v -> openImageChooser());

        view.findViewById(R.id.btnFinish).setOnClickListener(v -> {
            // Lấy thông tin từ các bước trước
            Bundle args = getArguments();
            if (args == null) return;

            String fullName = args.getString("fullName");
            String dob = args.getString("dob");
            String gender = args.getString("gender");
            String avatarUrl = args.getString("avatar");

            // Nếu có ảnh mới, resize và chuyển sang base64 rồi upload lên Firestore
            if (selectedImageUri != null) {
                try {
                    String avatarBase64 = convertImageToBase64(selectedImageUri);
                    saveUserProfile(fullName, dob, gender, avatarBase64);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Xử lý lỗi
                }
            } else {
                // Nếu không có ảnh mới, giữ nguyên avatar URL từ Bundle
                saveUserProfile(fullName, dob, gender, avatarUrl);
            }
        });

        return view;
    }

    // Mở bộ chọn ảnh từ thiết bị
    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Avatar"), PICK_IMAGE_REQUEST);
    }

    // Xử lý khi người dùng chọn ảnh
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            ImageView ivAvatar = getView().findViewById(R.id.ivAvatar);
            Glide.with(this).load(selectedImageUri).into(ivAvatar); // Hiển thị ảnh đã chọn
        }
    }

    // Chuyển đổi ảnh thành base64
    private String convertImageToBase64(Uri imageUri) throws IOException {
        // Mở InputStream từ URI ảnh
        InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        // Resize ảnh (chỉnh kích thước phù hợp cho avatar)
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true); // 200x200 là kích thước avatar

        // Chuyển đổi Bitmap thành Base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    // Lưu thông tin người dùng vào Firestore
    private void saveUserProfile(String fullName, String dob, String gender, String avatarBase64) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> userProfile = new HashMap<>();
        userProfile.put("fullName", fullName);
        userProfile.put("dob", dob);
        userProfile.put("gender", gender);
        userProfile.put("avatar", avatarBase64);

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .set(userProfile)
                .addOnSuccessListener(aVoid -> {
                    // Sau khi lưu thành công, chuyển đến FinishFragment
                    // Chuyển đến FullNameFragment
                    ((CompleteProfileActivity) getActivity()).navigateToNextFragment(new FinishFragment());
                })
                .addOnFailureListener(e -> {
                    // Thông báo lỗi nếu có
                });
    }
}
