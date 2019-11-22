package io.github.freeenglish.questions
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.github.freeenglish.R
import io.github.freeenglish.ViewModels.QuestionsViewModel
import io.github.freeenglish.ViewModels.ScreenState
import io.github.freeenglish.data.AppDatabase
import io.github.freeenglish.utils.viewModels
import kotlinx.android.synthetic.main.questions_fragment.*

class QuestionFragment : Fragment() {

    var buttons: Array<AppCompatButton?> = emptyArray()

    companion object {
        fun newInstance() = QuestionFragment()
    }

    private val viewModel: QuestionsViewModel by viewModels {
        QuestionsViewModel(AskUserUseCaseImplementation(AppDatabase.getInstance(context!!).questionsDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.questions_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttons = arrayOf(button1, button2, button3, button4)
        viewModel.state.observe(viewLifecycleOwner,
            Observer { state -> updateQuestionState(state as ScreenState.QuestionState)})
    }

    private fun updateQuestionState(state: ScreenState.QuestionState) {
        question.text = state.question.question

        buttons.forEachIndexed { index, appCompatButton ->
            if (state.question.answers.size > index) {
                appCompatButton?.visibility = View.VISIBLE
                appCompatButton?.text = state.question.answers[index].value
            } else {
                appCompatButton?.visibility = View.INVISIBLE

            }
        }
    }


}
