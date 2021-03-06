package ru.skillbranch.devintensive.ui.profile

import android.graphics.*
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.ui.custom.CircleImageView
import ru.skillbranch.devintensive.utils.Utils
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel

class ProfileActivity : AppCompatActivity() {
    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
        const val ERROR = "Невалидный адрес репозитория"
    }

    private lateinit var viewModel: ProfileViewModel
    lateinit var viewFields : Map<String, TextView>
    private var isEditMode = false
    private var isRepoCorrect = true

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initViews(savedInstanceState)
        initViewModel()
        Log.d("M_ProfileActivity", "onCreate")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(IS_EDIT_MODE, isEditMode)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer { updateUI(it) })
        viewModel.getTheme().observe(this, Observer { updateTheme(it) })
    }

    private fun updateTheme(mode: Int) {
        Log.d("M_ProfileActivity", "update theme")
        delegate.setLocalNightMode(mode)
    }

    private fun updateUI(profile: Profile) {
        profile.toMap().also {
            for ((k,v) in viewFields) {
                v.text = it[k].toString()
            }
        }

        drawInitials()
    }

    private fun initViews(savedInstanceState: Bundle?) {
        viewFields = mapOf(
            "nickname" to tv_nick_name,
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
        )

        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false
        showCurrentMode(isEditMode)

        btn_edit.setOnClickListener {
            if (isEditMode && isRepoCorrect) saveProfileInfo()
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)
        }

        btn_switch_theme.setOnClickListener {
            viewModel.switchTheme()
        }

        et_repository.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val repo = et_repository.text.toString()
                Log.d("M_ProfileActivity", repo)
                if (!validateRepository(repo)) {
                    wr_repository.error = ERROR
                    isRepoCorrect = false
                } else {
                    wr_repository.error = null
                    isRepoCorrect = true
                }
            }

        })
    }

    private fun validateRepository(repoName: String): Boolean {
        val regex = """^(https://|www.|https://www.)?github.com/(?!(enterprise|features|topics|collections|trending|events|marketplace|pricing|nonprofit|customer-stories|security|login|join)$)[A-z0-9_-]+""".toRegex()
        return regex.matches(repoName) or repoName.isEmpty()
    }

    private fun showCurrentMode(isEdit: Boolean) {
        val info = viewFields.filter {
            setOf(
                "firstName",
                "lastName",
                "about",
                "repository"
            ).contains(it.key)
        }
        for ((_, v) in info) {
            v as EditText
            v.isFocusable = isEdit
            v.isFocusableInTouchMode = isEdit
            v.isEnabled = isEdit
            v.background.alpha = if (isEdit) 255 else 0
        }

        ic_eye.visibility = if (isEdit) View.GONE else View.VISIBLE
        wr_about.isCounterEnabled = isEdit

        with(btn_edit) {
            val filter: ColorFilter? = if (isEdit) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.color_accent, theme),
                    PorterDuff.Mode.SRC_IN
                )
            } else {
                null
            }
            val icon = if (isEdit) {
                resources.getDrawable(R.drawable.ic_save_black_24dp, theme)
            } else {
                resources.getDrawable(R.drawable.ic_edit_black_24dp, theme)
            }
            background.colorFilter = filter
            setImageDrawable(icon)
        }


    }
    private fun saveProfileInfo() {
        Profile(
            firstName = et_first_name.text.toString(),
            lastName = et_last_name.text.toString(),
            about = et_about.text.toString(),
            repository = et_repository.text.toString()
        ).apply {
            viewModel.saveProfileData(this)
        }
    }

    private fun drawInitials() {
        val circleImageView: CircleImageView = findViewById(R.id.iv_avatar)
        val size = resources.getDimension(R.dimen.avatar_round_size).toInt()
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        circleImageView.setImageBitmap(bitmap)

        val text = Utils.toInitials(
            Utils.transliteration(et_first_name.text.toString()),
            Utils.transliteration(et_last_name.text.toString())
        ) ?: ""
        val mPaint = Paint()
        val mTextBoundRect = Rect()

        val width = resources.getDimension(R.dimen.avatar_round_size)
        val height = resources.getDimension(R.dimen.avatar_round_size)


        val centerX = width / 2
        val centerY = height / 2

        val value = TypedValue()
        theme.resolveAttribute(R.attr.colorAccent, value, true)
        canvas.drawColor(value.data)

        Log.d("M_ProfileActivity", "smth")
        mPaint.color = Color.WHITE
        mPaint.textSize = resources.getDimension(R.dimen.avatar_initials_48)

        mPaint.getTextBounds(text, 0, text.length, mTextBoundRect)
        val mTextWidth = mPaint.measureText(text)
        val mTextHeight = mTextBoundRect.height()

        canvas.drawText(
            text,
            centerX - (mTextWidth / 2f),
            centerY + (mTextHeight / 2f),
            mPaint
        )

        circleImageView.setImageBitmap(bitmap)
    }

}
