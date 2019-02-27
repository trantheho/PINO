package com.example.loginpino.tutorial

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.util.Log
import android.widget.Toast
import com.example.loginpino.Myapp
import com.example.loginpino.R
import com.example.loginpino.tutorial.adapter.TutorialAdapter
import com.example.loginpino.tutorial.adapter.TutorialAdapterListener
import com.example.loginpino.tutorial.pdf.PdfViewActivity
import com.example.loginpino.tutorial.video.YoutubeViewActivity
import kotlinx.android.synthetic.main.activity_tutorial.*
import java.util.ArrayList

class TutorialActivity : AppCompatActivity() {

    private lateinit var items: List<TutorialItem>

    val pdfViewActivity = PdfViewActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        initTutorialItem()
        initRecylerView()
        val intro = "<p>" + "- Giúp nhà trường chia sẻ thông tin về tình hình học tập của học sinh một cách đầy đủ, nhanh chóng tới phụ huynh." + "<br>" +
                "- Giúp phụ huynh thường xuyên phối hợp với nhà trường đôn đốc việc học của con em mình." + "<br>" +
                "- Các thông tin cần trao đổi được chia theo từng mục, từng dạng nhỏ một cách khoa học, dễ nhìn giúp theo dõi dễ dàng." + "<br>" +
                "- Giúp phụ huynh dễ dàng đánh giá quá trình học tập của học sinh một cách toàn diện." + "</p>"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvIntro.text = Html.fromHtml(intro, Html.FROM_HTML_MODE_COMPACT)
        } else {
            tvIntro.text = Html.fromHtml(intro)
        }

    }

    private fun initRecylerView() {
        val adapter = TutorialAdapter(items)
        rcl_tutorial.layoutManager = LinearLayoutManager(this)
        rcl_tutorial.hasFixedSize()
        rcl_tutorial.isNestedScrollingEnabled = false
        rcl_tutorial.adapter = adapter
        adapter.onClick(object : TutorialAdapterListener {
            override fun onClickNormal(position: Int) {
                pdfViewActivity.startActivity(this@TutorialActivity, items.get(position).title.toString(), items.get(position).link.toString())
            }

            override fun onClickVideo(position: Int) {
                val intent = Intent(this@TutorialActivity, YoutubeViewActivity::class.java)
                startActivity(intent)
            }
        })
        scrollView.parent.requestChildFocus(scrollView, scrollView)
    }


     private fun initTutorialItem() {
         items = ArrayList<TutorialItem>()
        (items as ArrayList<TutorialItem>).add(TutorialItem("Chức năng mới", "Bảo vệ tài khoản, quên mật khẩu…", R.drawable.ic_newversion, "https://raw.githubusercontent.com/huyqv/assets/master/images/01.jpg"))
        (items as ArrayList<TutorialItem>).add(TutorialItem("Các vấn đề tài khoản", "Bảo vệ tài khoản, quên mật khẩu…", R.drawable.ic_account, "http://appstore.nhatcuong.vn/PINO/Guid/taikhoan.pdf"))
        (items as ArrayList<TutorialItem>).add(TutorialItem("Sử dụng ứng dụng", "Cách dùng chức năng", R.drawable.ic_user_app, "http://appstore.nhatcuong.vn/PINO/Guid/chucnang.pdf"))
        (items as ArrayList<TutorialItem>).add(TutorialItem("Chức năng ví", "Giúp thanh toán nhanh các khoản phí", R.drawable.ic_taovi, "http://appstore.nhatcuong.vn/PINO/Guid/vi.pdf"))
        (items as ArrayList<TutorialItem>).add(TutorialItem("Video hướng dẫn", "Hướng dẫn sử dụng bằng video", R.drawable.ic_youtube))
    }

}